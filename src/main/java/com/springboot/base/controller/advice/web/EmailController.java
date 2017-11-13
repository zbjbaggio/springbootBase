package com.springboot.base.controller.advice.web;

import com.springboot.base.data.dto.EmailDTO;
import com.springboot.base.data.enmus.ErrorInfo;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.util.BindingResultUtils;
import com.springboot.base.util.EmailUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 描述：
 * Created by jay on 2017-11-13.
 */
@RestController
@RequestMapping("/email")
@Slf4j
public class EmailController {

    @RequestMapping(value = "/email", method = RequestMethod.POST)
    public void email() throws Exception {
        EmailUtils.sendEmail(new EmailDTO("你大爷","215344388@qq.com", "大侄子","测试一下","你看看行不行"));
    }
}
