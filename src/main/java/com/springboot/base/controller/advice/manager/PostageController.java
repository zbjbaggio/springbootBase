package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.base.Page;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.PostageInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.PostageVO;
import com.springboot.base.service.PostageService;
import com.springboot.base.util.BindingResutlUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 描述：
 * Created by jay on 2017-11-24.
 */
@RestController
@RequestMapping("/manage/user/postageInfo")
@Slf4j
public class PostageController {

    @Inject
    private PostageService postageService;

    /**
     * 邮费查询
     *
     * @param limit
     * @param offset
     * @param searchStr
     * @return
     */
    @GetMapping(value = "/list")
    public Page list(@RequestParam(value = "limit", defaultValue = "10") int limit,
                     @RequestParam(value = "offset", defaultValue = "0") int offset,
                     @RequestParam(value = "searchStr", defaultValue = "-1") String searchStr,
                     @RequestParam(value = "orderBy", defaultValue = "") String orderBy,
                     @RequestParam(value = "desc") boolean desc) {
        return postageService.listPage(limit, offset, searchStr, orderBy, desc);
    }

    /**
     * 添加邮费
     *
     * @param postageInfo
     * @return
     */
    @PostMapping(value = "/add")
    public PostageInfo add(@RequestBody @Validated(PostageInfo.BaseInfo.class) PostageInfo postageInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        return postageService.save(postageInfo);
    }

    /**
     * 详情
     *
     * @param postageId
     * @return
     */
    @GetMapping(value = "/getDetail")
    public PostageVO getDetail(@RequestParam(value = "postageId") Long postageId) {
        return postageService.getDetail(postageId);
    }

    /**
     * 邮费修改
     * @param postageInfo
     * @param bindingResult
     * @throws Exception
     */
    @PostMapping(value = "/update")
    public void update(@RequestBody @Validated(PostageInfo.Modify.class) PostageInfo postageInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("添加验证信息{}", BindingResutlUtils.getMessage(bindingResult));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        postageService.update(postageInfo);
    }

    /**
     * 邮费删除
     * @param postageIds
     * @throws Exception
     */
    @PostMapping(value = "/delete")
    public void delete(@RequestParam Long[] postageIds) throws Exception {
        if (postageIds == null || postageIds.length <= 0) {
            log.info("postageIds为空！");
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        postageService.delete(postageIds);
    }
}
