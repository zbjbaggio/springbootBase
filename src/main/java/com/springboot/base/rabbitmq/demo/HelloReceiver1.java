package com.springboot.base.rabbitmq.demo;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Consumer;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;


/**
 * 描述：
 * Created by jay on 2018-9-12.
 */
@Component
@RabbitListener(queues = "helloA")
public class HelloReceiver1 {

    @RabbitHandler
    public void process(String hello, Channel channel, Message message) throws Exception {
        channel.basicQos(5);
        //channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        if ( hello.substring(0, 6).equals("1hello") || hello.substring(0, 6).equals("3hello")) {
            try {
                System.out.println("HelloReceiverA收到  : " + hello + "收到时间" + new Date());
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("----error--------" + hello);
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}
