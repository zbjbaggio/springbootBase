package com.springboot.base.service.impl;

import com.springboot.base.constant.SystemPropertiesConstants;
import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.MenuAndButtonDTO;
import com.springboot.base.data.dto.PasswordDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.mapper.ManagerInfoMapper;
import com.springboot.base.service.ManagerInfoService;
import com.springboot.base.service.PermissionInfoService;
import com.springboot.base.service.RedisService;
import com.springboot.base.util.DateUtil;
import com.springboot.base.util.PasswordUtil;
import com.springboot.base.util.StringUtil;
import com.springboot.base.util.TokenUtils;
import com.springboot.base.util.ValueHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 用户信息
 * Created by jay on 2017-4-10.
 */
@Service
@Slf4j
public class ManagerInfoServiceImpl implements ManagerInfoService {

    @Inject
    private ManagerInfoMapper managerInfoMapper;

    @Inject
    private RedisService redisService;

    @Inject
    private PermissionInfoService permissionInfoService;

    @Inject
    private ValueHolder valueHolder;

    // TODO: 2017-10-12 未完成：1.登录成功时删除该用户登录错误次数 2.不能登录一个小时后，再猜错同样次数的直接锁定 3.同样ip地址猜错一次密码出验证码
    @Override
    public ManagerVO login(ManagerInfo user) throws Exception {
        ManagerInfo newManagerInfo = managerInfoMapper.getUserInfo(user.getUsername());
        if (newManagerInfo == null || !checkUser(user.getPassword(), newManagerInfo)) {
            return null;
        }
        newManagerInfo.setPasswordNumber(0);
        newManagerInfo.setPassword("");
        redisService.removeUserPasswordNumberByKey(newManagerInfo.getUsername());
        MenuAndButtonDTO menuAndButtonDTO = permissionInfoService.getMenu(newManagerInfo.getId());
        newManagerInfo.setPermissionSet(menuAndButtonDTO.getPermissionSet());
        saveRedis(newManagerInfo, true);
        ManagerVO managerVO = new ManagerVO();
        BeanUtils.copyProperties(newManagerInfo, managerVO);
        //获得菜单
        managerVO.setPermissionList(menuAndButtonDTO.getMenuList());
        managerVO.setButtonSet(menuAndButtonDTO.getButtonSet());
        return managerVO;
    }

    @Override
    public boolean checkToken(String token, String key, String url) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(key)) {
            log.info("未登录！");
            return false;
        }
        ManagerInfo managerInfo = redisService.getUserInfoByKey(key);
        if (managerInfo != null && token.equals(managerInfo.getToken())) {
            // TODO: 2018-1-10 先不拦截权限
/*            Set<String> permissionSet = managerInfo.getPermissionSet();
            //用户本身功能不限制权限
            if (!url.contains("/manage/user/me/") && !permissionSet.contains(url)) {
                log.info("{}未授权！managerInfo:{}", url, managerInfo);
                return false;
            }*/
            valueHolder.setUserIdHolder(managerInfo.getId());
            redisService.saveUser(managerInfo);
            return true;
        }
        log.info("重新登录, token:{}, key:{}, managerInfo:{}！", token, key, managerInfo);
        return false;
    }

    @Override
    public Page listPage(int limit, int offset, String searchStr, int status, String orderBy, boolean desc) {
        if (!"-1".equals(searchStr)) {
            searchStr = "%" + searchStr + "%";
        }
        String descStr = "";
        if (!StringUtils.isEmpty(orderBy) && desc) {
            descStr = "desc";
        }
        Page page = new Page();
        Long count = managerInfoMapper.count(searchStr, status);
        if (count != 0) {
            page.setCount(count);
            page.setList(managerInfoMapper.listPage(limit, offset, searchStr, status, orderBy, descStr));
        }
        return page;
    }

    @Override
    public ManagerVO getDetail(Long userId) {
        return managerInfoMapper.getDetailById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(ManagerInfo managerInfo) throws Exception {
        //校验用户名称是否重复
        checkUsernameByUserId(managerInfo.getUsername(), managerInfo.getId());
        int count = managerInfoMapper.update(managerInfo);
        if (count <= 0) {
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, UserStatus index) throws Exception {
        int i = managerInfoMapper.updateStatus(index.getIndex(), userId);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.STATUS_ERROR);
        }
    }

    /**
     * 解锁用户状态
     * @param userId
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void unlockedUserStatus(Long userId) throws Exception {
        updateStatus(userId, UserStatus.DEFAULT);
        ManagerVO newManagerInfo = getDetail(userId);
        redisService.removeUserPasswordNumberByKey(newManagerInfo.getUsername());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void remove(Long[] userIds) throws Exception {
        int i = managerInfoMapper.updateDr(userIds);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.DELETE_ERROR);
        }
    }

    @Override
    public void loginOut() throws Exception {
        ManagerInfo user = managerInfoMapper.getById(valueHolder.getUserIdHolder());
        redisService.removeUserTokenByKey(TokenUtils.getKey(user));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(PasswordDTO passwordDTO) throws Exception {
        Long userIdHolder = valueHolder.getUserIdHolder();
        ManagerInfo user = managerInfoMapper.getById(userIdHolder);
        if (user == null) {
            log.error("userId未能查询出用户数据！userId:{}", userIdHolder);
            throw new PrivateException(ErrorInfo.ERROR);
        }
        String password = PasswordUtil.getPassword(passwordDTO.getOldPassword(), user.getSalt());
        if (!user.getPassword().equals(password)) {
            log.info("userId:{}, oldPassword:{}, password:{}", userIdHolder, password, user.getPassword());
            throw new PrivateException(ErrorInfo.PASSWORD_ERROR);
        }
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        password = PasswordUtil.getPassword(passwordDTO.getNewPassword(), salt);
        int count = managerInfoMapper.updatePassword(userIdHolder, password, salt);
        if (count <= 0) {
            throw new PrivateException(ErrorInfo.UPDATE_ERROR);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ManagerInfo save(ManagerInfo managerInfo) throws Exception {
        //校验用户名称是否重复
        int count;
        Long id = managerInfo.getId();
        checkUsernameByUserId(managerInfo.getUsername(), id == null ? -1 : id);
        if (managerInfo.getId() == null) {
            UUID uuid = UUID.randomUUID();
            String salt = uuid.toString();
            managerInfo.setSalt(salt);
            managerInfo.setPassword(PasswordUtil.getPassword(managerInfo.getPassword(), salt));
            managerInfo.setStatus(UserStatus.DEFAULT.getIndex());
            Long userIdHolder = valueHolder.getUserIdHolder();
            managerInfo.setOperatorId(userIdHolder);
            count = managerInfoMapper.save(managerInfo);
        } else {
            count = managerInfoMapper.update(managerInfo);
        }
        if (count != 1) {
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
        return managerInfo;
    }

    private void checkUsernameByUserId(String username, Long userId) throws Exception {
        ManagerInfo user = managerInfoMapper.getUserInfoNoStateNoId(username, userId);
        if (user != null) {
            throw new PrivateException(ErrorInfo.USER_NAME_SAME);
        }
    }

    //设置token
    private void saveRedis(ManagerInfo managerInfo, boolean isToken) throws Exception {
        managerInfo.setKey(TokenUtils.getKey(managerInfo));
        if (isToken) {
            managerInfo.setToken(TokenUtils.getToken(managerInfo));
        }
        redisService.saveUser(managerInfo);
    }

    //校验密码
    private boolean checkUser(String passwordStr, ManagerInfo newManagerInfo) throws Exception {
        if (newManagerInfo.getStatus() == UserStatus.LOCKED.getIndex()) {
            log.info("该用户被锁定！username：{}", newManagerInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_LOCKED);
        }
        if (newManagerInfo.getStatus() == UserStatus.UNACTIVATED.getIndex()) {
            log.info("该用户还未审核通过！username：{}", newManagerInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_UNACTIVATED);
        }
        //查看是否被冻结
        Integer number = checkPasswordNumber(newManagerInfo.getUsername());
        String password = PasswordUtil.getPassword(passwordStr, newManagerInfo.getSalt());

        boolean result = password.equals(newManagerInfo.getPassword());
        if (!result) {
            redisService.saveUserPasswordNumber(newManagerInfo.getUsername(), ++number);
            log.info("用户密码校验错误，失败次数：{}", number);
            checkAndSaveExpectNumber(newManagerInfo.getUsername(), number, newManagerInfo.getId());
        }
        return result;
    }

    //校验猜密码次数
    private Integer checkPasswordNumber(String username) throws Exception {
        Integer number = redisService.getUserPasswordNumber(username);
        if (number >= SystemPropertiesConstants.frozenNumber) {
            log.info("该用户被停止登录！username：{}", username);
            throw new PrivateException(ErrorInfo.USER_NO_LOGIN);
        }
        return number;
    }

/*    //校验猜密码次数+ip
    private Integer checkPasswordNumberSameIP(String username, String ip) throws Exception {
        Integer number = redisService.getUserPasswordNumberSameIP(username, ip);
        if (number >= systemPropertiesConstants.getMANAGER_LOGIN_FROZEN_NUMBER()) {
            log.info("该用户被停止登录！username：{}, ip:{}, number:{}", username, ip, number);
            throw new PrivateException(ErrorInfo.USER_NO_LOGIN);
        }
        return number;
    }*/

    //校验是否存储欲锁定次数
    @Transactional
    private void checkAndSaveExpectNumber(String username, int lockNumber, Long userId) throws Exception {
        //判断是否达到冻结上限
        if (lockNumber >= SystemPropertiesConstants.frozenNumber) {
            int lockedNumber = redisService.getUserExpectNumber(username);
            lockedNumber++;
            //判断是否达到锁定上限
            if (lockedNumber >= SystemPropertiesConstants.lockedNumber) {
                //锁定用户账户
                updateStatus(userId, UserStatus.LOCKED);
                log.info("该用户账户已被锁定！username:{}", username);
                throw new PrivateException(ErrorInfo.USER_LOCKED);
            } else {
                redisService.saveUserExpectNumber(username, lockedNumber);
            }
        }
    }
}
