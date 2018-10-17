package com.springboot.base.rabbitmq;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import com.springboot.base.constant.QueueConstants;
import com.springboot.base.data.dto.QueueMessageDTO;
import com.springboot.base.enmus.MessageTypeEnum;
import com.springboot.base.service.MessageQueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 描述：死信消息转发队列
 * Created by jay on 2018-10-17.
 */
@Component
@RabbitListener(queues = QueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME)
@Slf4j
public class TradeProcessorReceiver  {

    @Autowired
    private MessageQueueService messageQueueService;

    @RabbitHandler
    public void process(String content, Channel channel, Message message) throws IOException {
        QueueMessageDTO messageDTO = JSONObject.parseObject(content, QueueMessageDTO.class);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        messageDTO.setType(MessageTypeEnum.DEFAULT.getIndex());
        messageQueueService.send(messageDTO);
    }

}
