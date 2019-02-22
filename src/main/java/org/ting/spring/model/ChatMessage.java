package org.ting.spring.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChatMessage {

    //信息发送者
    private User user;

    //接受者
    private User targetUser;

    //信息类型 user 个人信息 group 群发
    private String type;

    private String content;

    private LocalDateTime localDateTime;
}
