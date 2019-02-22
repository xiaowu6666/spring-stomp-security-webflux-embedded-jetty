package org.ting.spring.stomp.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.ting.spring.model.User;
import org.ting.spring.service.UserService;
import org.ting.spring.utils.Online;

import java.security.Principal;
import java.util.Date;

/**
 * 注册连接web socket 监听器
 */
@Component
@Slf4j
public class WebSocketSessionConnectEvent implements ApplicationListener<SessionConnectEvent>{

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @Autowired
    private UserService userService;

    @Override
    public void onApplicationEvent(SessionConnectEvent event) {
        Principal principal = event.getUser();
        log.info("client name: {} connect.....",principal.getName());
        if (principal != null){
            User user = userService.findByEmail(principal.getName());
            Online.add(user);
            messageTemplate.convertAndSend("/topic/user.list",Online.onlineUsers());
        }
    }
}
