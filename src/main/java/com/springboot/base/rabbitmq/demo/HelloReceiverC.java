package com.springboot.base.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


/**
 * 描述：
 * Created by jay on 2018-9-12.
 */
@Component
//@RabbitListener(queues = "helloC")
public class HelloReceiverC {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void process(String hello, Channel channel, Message message) throws Exception {
        System.out.println("HelloReceiverC收到  : " + hello + "收到时间" + new Date());
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        rabbitTemplate.convertAndSend("hellos", "111111111111");
    }

}
