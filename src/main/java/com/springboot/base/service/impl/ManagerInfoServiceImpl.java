package com.springboot.base.service.impl;

import com.springboot.base.constant.SystemConstants;
import com.springboot.base.data.base.Page;
import com.springboot.base.data.dto.PasswordDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.mapper.ManagerInfoMapper;
import com.springboot.base.service.ManagerInfoService;
import com.springboot.base.service.RedisService;
import com.springboot.base.util.PasswordUtil;
import com.springboot.base.util.TokenUtils;
import com.springboot.base.util.ValueHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.inject.Inject;
import java.util.UUID;

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
    private ValueHolder valueHolder;

    // TODO: 2017-10-12 未完成：1.登录成功时删除该用户登录错误次数 2.不能登录一个小时后，再猜错同样次数的直接冻结 3.同样ip地址猜错一次密码出验证码
    @Override
    public ManagerVO login(ManagerInfo user) throws Exception {
        Integer number = checkPasswordNumber(user.getUsername());
        ManagerInfo newManagerInfo = managerInfoMapper.getUserInfo(user.getUsername());
        if (newManagerInfo == null || !checkUser(user.getPassword(), newManagerInfo)) {
            redisService.saveUserPasswordNumber(user.getUsername(), number + 1);
            return null;
        }
        newManagerInfo.setPasswordNumber(0);
        newManagerInfo.setPassword("");
//        redisService.removeUserPasswordNumberByKey();
        saveRedis(newManagerInfo, true);
        ManagerVO managerVO = new ManagerVO();
        BeanUtils.copyProperties(newManagerInfo, managerVO);
        return managerVO;
    }

    @Override
    public boolean checkToken(String token, String key) {
        if (StringUtils.isEmpty(token) || StringUtils.isEmpty(key)) {
            log.info("未登录");
            return false;
        }
        ManagerInfo managerInfo = redisService.getUserInfoByKey(key);
        if (managerInfo != null && token.equals(managerInfo.getToken())) {
            valueHolder.setUserIdHolder(managerInfo.getId());
            redisService.saveUser(managerInfo);
            return true;
        }
        log.info("重新登录, token:{}, key:{}, managerInfo:{}", token, key, managerInfo);
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
    public void update(ManagerInfo managerInfo) throws Exception {
        //校验用户名称是否重复
        checkUsernameByUserId(managerInfo.getUsername(), managerInfo.getId());
        int count = managerInfoMapper.update(managerInfo);
        if (count <= 0) {
            throw new PrivateException(ErrorInfo.SAVE_ERROR);
        }
    }

    @Override
    public void updateStatus(Long userId, UserStatus index) throws Exception {
        int i = managerInfoMapper.updateStatus(index.getIndex(), userId);
        if (i <= 0) {
            throw new PrivateException(ErrorInfo.STATUS_ERROR);
        }
    }

    @Override
    public void delete(Long[] userIds) throws Exception {
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
    public ManagerInfo save(ManagerInfo managerInfo) throws Exception {
        //校验用户名称是否重复
        checkUsername(managerInfo.getUsername());
        UUID uuid = UUID.randomUUID();
        String salt = uuid.toString();
        managerInfo.setSalt(salt);
        managerInfo.setPassword(PasswordUtil.getPassword(managerInfo.getPassword(), salt));
        managerInfo.setStatus(UserStatus.DEFAULT.getIndex());
        Long userIdHolder = valueHolder.getUserIdHolder();
        managerInfo.setOperatorId(userIdHolder);
        int count = managerInfoMapper.save(managerInfo);
        if (count > 0) {
            return managerInfo;
        }
        return null;
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

    private void checkUsername(String username) throws Exception {
        ManagerInfo user = managerInfoMapper.getUserInfoNoState(username);
        if (user != null) {
            throw new PrivateException(ErrorInfo.USER_NAME_SAME);
        }
    }

    //校验密码
    private boolean checkUser(String passwordStr, ManagerInfo newManagerInfo) throws Exception {
        if (newManagerInfo.getStatus() == UserStatus.FREEZE.getIndex()) {
            log.info("该用户被冻结！username：{}", newManagerInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_FREEZE);
        }
        if (newManagerInfo.getStatus() == UserStatus.UNACTIVATED.getIndex()) {
            log.info("该用户还未审核通过！username：{}", newManagerInfo.getUsername());
            throw new PrivateException(ErrorInfo.USER_UNACTIVATED);
        }
        String password = PasswordUtil.getPassword(passwordStr, newManagerInfo.getSalt());
        return password.equals(newManagerInfo.getPassword());
    }

    //校验猜密码次数
    private Integer checkPasswordNumber(String username) throws Exception {
        Integer number = redisService.getUserPasswordNumber(username);
        if (number >= SystemConstants.FREEZE_NUMBER) {
            log.info("该用户被停止登录！username：{}", username);
            throw new PrivateException(ErrorInfo.USER_NO_LOGIN);
        }
        return number;
    }
}
