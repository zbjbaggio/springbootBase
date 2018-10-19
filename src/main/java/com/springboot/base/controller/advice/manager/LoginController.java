package com.springboot.base.controller.advice.manager;

import com.springboot.base.data.dto.QueueMessageDTO;
import com.springboot.base.enmus.ErrorInfo;
import com.springboot.base.data.entity.ManagerInfo;
import com.springboot.base.data.exception.PrivateException;
import com.springboot.base.data.vo.ManagerVO;
import com.springboot.base.enmus.MessageTypeEnum;
import com.springboot.base.rabbitmq.demo.HelloSender1;
import com.springboot.base.service.ManagerInfoService;
import com.springboot.base.service.MessageQueueService;
import com.springboot.base.util.BindingResultUtils;
import com.sun.xml.internal.ws.api.model.MEP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;

/**
 * 登录
 * Created by jay on 2017-4-10.
 */
@RestController
@RequestMapping("/manage")
@Slf4j
public class LoginController {

    @Inject
    private ManagerInfoService managerInfoService;

    @Autowired
    private HelloSender1 helloSender1;

    /**
     * 登录接口
     *
     * @param managerInfo
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ManagerVO login(@RequestBody @Validated(ManagerInfo.LoginGroup.class) ManagerInfo managerInfo, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            log.info("参数错误！{}", BindingResultUtils.getErrorMessage(bindingResult.getAllErrors()));
            throw new PrivateException(ErrorInfo.PARAMS_ERROR);
        }
        ManagerVO managerVO = managerInfoService.login(managerInfo);
        if (managerVO == null) {
            throw new PrivateException(ErrorInfo.LOGIN_ERROR);
        }
        return managerVO;
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public void test(@RequestParam int id) throws Exception {
       helloSender1.send(id);
/*        MessagePostProcessor processor = message1 -> {
            message1.getMessageProperties().setExpiration(30000 + "");
            return message1;
        };
        rabbitTemplate.convertAndSend("helloExchange", "helloB", (Object) "1111111111111111111", processor);*/
    }
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * http://localhost:8080/send?message=hello
     *
     * @param message
     * @return
     */
    @RequestMapping("/send")
    public void sendMQ(String message) {
        //CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        //rabbitTemplate.convertAndSend("my-queue", (Object) message,correlationData);
        MessagePostProcessor processor = message1 -> {
            message1.getMessageProperties().setExpiration(10000 + "");
            return message1;
        };
        rabbitTemplate.convertAndSend("helloExchange", "helloB", (Object) "1111111111111111111", processor);
    }

    @Inject
    private MessageQueueService messageQueueService;

    @GetMapping("/send1")
    public void send1(String message) {
        QueueMessageDTO messageDTO = new QueueMessageDTO("aaaa", "1hello");
        messageDTO.setSeconds(10);
        messageDTO.setType(MessageTypeEnum.DELAYED.getIndex());
        messageQueueService.send(messageDTO);
    }

    @GetMapping("/send2")
    public void send2(String message) {
        rabbitTemplate.convertAndSend("fanoutExchange", "", (Object) "1111111111111111111");
    }

    @GetMapping("/send3")
    public void send3(String message) {
        int i = 1;
        while (true) {
            rabbitTemplate.convertAndSend("hellos", (Object) i++);
        }
    }


}
