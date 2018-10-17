package com.springboot.base.rabbitmq.demo;


import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by yanggm
 */
@Component
public class ReceiveDirect {

    @RabbitListener(queues = "my-queue")
    public void receiveMessage(Message message, Channel channel) throws IOException {
        String messageRec = new String(message.getBody());
        System.out.println("接收到的字符串消息是 => " + messageRec);
        //if ( message.equals("2")) {
            try {
                channel.basicQos(20);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e) {
                e.printStackTrace();
            }
       /* } else {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }*/
    }

}