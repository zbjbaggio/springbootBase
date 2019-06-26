package com.springboot.base.data.dto;

import com.springboot.base.enmus.MessageTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 描述：消息队列队列
 * Created by jay on 2018-10-17.
 */
@Data
@NoArgsConstructor
public class QueueMessageDTO {

    /**
     * 交换机名字
     */
    private String exchange;

    /**
     * 是否需要收到通知
     */
    private boolean isReturn;

    /**
     * 队列名字
     */
    private String queueName;

    /**
     * 类型，查看 MessageTypeEnum
     */
    private MessageTypeEnum type;

    /**
     * 消息组(group)
     */
    private Integer group;

    /**
     * 时间
     */
    private Date timestamp;

    /**
     * 消息内容
     */
    private String message;

    private Integer status;

    private int retry = 0;

    private int maxRetry = 10;

    /**
     * 死信队列时间，单位为秒
     */
    private int seconds = 0;

    public QueueMessageDTO(String queueName, String message) {
        this.queueName = queueName;
        this.message = message;
        this.timestamp = new Date();
        this.isReturn = false;
        this.type = MessageTypeEnum.DEFAULT;
    }

    public QueueMessageDTO(String queueName, String message, boolean isReturn) {
        this.queueName = queueName;
        this.message = message;
        this.timestamp = new Date();
        this.isReturn = isReturn;
        this.type = MessageTypeEnum.DEFAULT;
    }

    public boolean getIsReturn() {
        return isReturn;
    }

    public void setIsReturn(boolean isReturn) {
        this.isReturn = isReturn;
    }

}
