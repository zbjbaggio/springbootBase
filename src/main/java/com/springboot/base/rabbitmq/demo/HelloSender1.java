package com.springboot.base.rabbitmq.demo;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 描述：
 * Created by jay on 2018-9-12.
 */
@Component
public class HelloSender1 implements RabbitTemplate.ReturnCallback,RabbitTemplate.ConfirmCallback {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(int i) {
        String context = i + "hello "  + ", " + new Date();
        System.out.println("Sender1 : " + context);
        rabbitTemplate.setReturnCallback(this);
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend("cccadaf", context);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString() + "===" + i + "===" + s1 + "===" + s2);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
        } else {
            System.out.println("HelloSender1 消息发送成功 ");
        }
    }
}
