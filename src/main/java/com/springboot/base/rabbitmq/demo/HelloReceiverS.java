package com.springboot.base.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 描述：
 * Created by jay on 2018-9-12.
 */
@Component
public class HelloReceiverS {

    @RabbitListener(queues = "hellos")
    @RabbitHandler
    public void processA(String hello, Channel channel, Message message) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("Receiver2222  : " + hello);
    }


    @RabbitListener(queues = "fanout.B")
    @RabbitHandler
    public void processB(String hello, Channel channel, Message message) throws IOException {
       // channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("Receiver2  : " + hello);
    }

    @RabbitListener(queues = "fanout.C")
    @RabbitHandler
    public void processC(String hello, Channel channel, Message message) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        System.out.println("Receiver3  : " + hello);
    }

}
