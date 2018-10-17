package com.springboot.base.constant;

/**
 * 描述：队列名称常量
 * Created by jay on 2018-10-17.
 */
public interface QueueConstants {

    /**
     * 默认即时消息交换机名称
     */
    String DEFAULT_DIRECT_EXCHANGE_NAME = "default.direct.exchange";

    /**
     * 默认作为延时功能的死信队列名称
     */
    String DEFAULT_DEAD_LETTER_QUEUE_NAME = "default.dead.letter.queue";

    /**
     * 默认作为延时功能的死信消息转发的接收队列名称
     */
    String DEFAULT_REPEAT_TRADE_QUEUE_NAME = "default.repeat.trade.queue";
}
