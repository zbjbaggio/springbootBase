package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.enmus.UserStatus;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.service.ManagerInfoService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：后台管理员管理
 * Created by jay on 2017-9-29.
 */
@RestController
@RequestMapping("/manage/user/managerInfo")
@Slf4j
public class ManagerInfoController {

    @Inject
    private ManagerInfoService managerInfoService;

    /**
     * 用户查询
     * @param limit
     * @param offset
     * @param searchStr
     * @param status
     * @return
     */
    @GetMapping(value = "/list")
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "status", defaultValue = "-1") int status,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return managerInfoService.listPage(limit, offset, searchStr, status, orderBy, desc);
    }

    /**
     * 添加管理员
     * @param managerInfo
     * @return
     */
    @PostMapping(value = "/add")
    public ManagerInfo add(@RequestBody @Validated(ManagerInfo.BaseInfo.class) ManagerInfo managerInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return managerInfoService.save(managerInfo);
    }

    /**
     * 管理员详情
     * @param userId
     * @return
     */
    @GetMapping(value = "/detail")
    public ManagerVO detail(@RequestParam(value = "userId") Long userId) {
        return managerInfoService.getDetail(userId);
    }

    /**
     * 管理员修改
     * @param managerInfo
     * @param bindingResult
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public void update(@RequestBody @Validated(ManagerInfo.Modify.class) ManagerInfo managerInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        managerInfoService.update(managerInfo);
    }

    /**
     * 管理员冻结
     * @param userId
     * @throws Exception
     */
    @PostMapping(value = "/updateFreeze")
    public void updateFreeze(@RequestParam Long userId) throws Exception {
        if (userId == null) {
            log.info("userId为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        managerInfoService.updateStatus(userId, UserStatus.FREEZE);
    }

    /**
     * 管理员删除
     * @param userIds
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    public void delete(@RequestParam Long[] userIds) throws Exception {
        if (userIds == null || userIds.length <= 0) {
            log.info("userIds为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        managerInfoService.remove(userIds);
    }

}
