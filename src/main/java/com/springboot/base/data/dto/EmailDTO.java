package com.springboot.base.data.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailDTO {

    private String sendMailPersonal;//发件人昵称

    private String receiveMailAccount;//收件人地址

    private String receiveMailPersonal;//收件人昵称

    private String subject;//主题

    private String content;//内容

}
