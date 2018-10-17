package com.springboot.base.service;

import com.springboot.base.data.dto.QueueMessageDTO;

/**
 * 描述：
 * Created by jay on 2018-10-17.
 */
public interface MessageQueueService {

    void send(QueueMessageDTO message);

}
