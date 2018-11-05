package com.springboot.base.conf;

import com.springboot.base.constant.QueueConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：RabbitMQ队列配置
 * Created by jay on 2018-10-17.
 */
//@Configuration
public class RabbitMQConfig {

    /**
     * 默认及时消息交换机
     * @return
     */
    @Bean
    public DirectExchange defaultDirectExchange() {
        return new DirectExchange(QueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME);
    }

    /**
     * 默认延迟消息死信队列
     * @return
     */
    @Bean
    public Queue defaultDeadLetterQueue() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange", QueueConstants.DEFAULT_DIRECT_EXCHANGE_NAME);
        arguments.put("x-dead-letter-routing-key", QueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
        return new Queue(QueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME,true, false, false, arguments);
    }

    @Bean
    public Binding defaultDeadLetterBinding(Queue defaultDeadLetterQueue, DirectExchange defaultDirectExchange) {
        return BindingBuilder.bind(defaultDeadLetterQueue).to(defaultDirectExchange).with(QueueConstants.DEFAULT_DEAD_LETTER_QUEUE_NAME);
    }

    /**
     * 默认延迟消息死信接受转发消息队列
     * @return
     */
    @Bean
    public Queue defaultRepeatTradeQueue() {
        return new Queue(QueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
    }

    @Bean
    public Binding defaultRepeatTradeBinding(Queue defaultRepeatTradeQueue, DirectExchange defaultDirectExchange) {
        return BindingBuilder.bind(defaultRepeatTradeQueue).to(defaultDirectExchange).with(QueueConstants.DEFAULT_REPEAT_TRADE_QUEUE_NAME);
    }

}
