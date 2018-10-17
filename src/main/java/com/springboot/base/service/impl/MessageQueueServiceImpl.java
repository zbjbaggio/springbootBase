package com.springboot.base.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.springboot.base.constant.QueueConstants;
import com.springboot.base.data.dto.QueueMessageDTO;
import com.springboot.base.enmus.MessageTypeEnum;
import com.springboot.base.service.MessageQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * 描述：消息队列实现
 * Created by jay on 2018-10-17.
 */
@Service
@Slf4j
public class MessageQueueServiceImpl implements MessageQueueService, RabbitTemplate.ReturnCallback, RabbitTemplate.ConfirmCallback {

    @Inject
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(QueueMessageDTO message) {
        //checkMessage(message);
        if (message.getType() == MessageTypeEnum.DEFAULT.getIndex() || message.getSeconds() < 0) {//即时消息
            sendMessage(message);
        }
        if (message.getType() == MessageTypeEnum.DELAYED.getIndex()) {//延时消息
            sendTimeMessage(message);
        }
    }

    //TODO 成功返回通知
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString() + "===" + i + "===" + s1 + "===" + s2);
    }

    //TODO 通知发送是否成功操作
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (!ack) {
            System.out.println("HelloSender消息发送失败" + cause + correlationData.toString());
        } else {
            System.out.println("HelloSender1 消息发送成功 ");
        }
    }

    private void sendMessage(QueueMessageDTO message) {
        if (message.getIsReturn()) {
            rabbitTemplate.setReturnCallback(this);
            rabbitTemplate.setConfirmCallback(this);
        }
        rabbitTemplate.convertAndSend(message.getExchange(), message.getQueueName(), message.getMessage());
    }

    private void sendTimeMessage(QueueMessageDTO messageDTO) {
        long times = messageDTO.getSeconds() * 1000;//rabbit默认为毫秒级
        MessagePostProcessor processor = message -> {
            message.getMessageProperties().setExpiration(times + "");
            return message;
        };
        rabbitTemplate.convertAndSend(QueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME, QueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME, JSON.toJSONString(messageDTO), processor);
    }

    /*private void checkMessage(QueueMessageDTO message) {
        if (StringUtils.isNullOrEmpty(message.getExchange())) {
            throw new MessageException(10, "发送消息格式错误: 消息交换机(exchange)不能为空!");
        }
        if (message.getGroup() == null) {
            throw new MessageException(10, "发送消息格式错误: 消息组(group)不能为空!");
        }
        if (message.getType() == null) {
            throw new MessageException(10, "发送消息格式错误: 消息类型(type)不能为空!");
        }
        if (message.getStatus() == null) {
            throw new MessageException(10, "发送消息格式错误: 消息状态(status)不能为空!");
        }
        if (StringUtils.isNullOrEmpty(message.getQueueName())) {
            throw new MessageException(10, "发送消息格式错误: 消息目标名称(queueName)不能为空!");
        }
        if (StringUtils.isNullOrEmpty(message.getMessage())) {
            throw new MessageException(10, "发送消息格式错误: 消息内容(message)不能为空!");
        }
    }*/
}
