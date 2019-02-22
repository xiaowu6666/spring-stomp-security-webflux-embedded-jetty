package org.ting.spring.stomp.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.ting.spring.model.User;
import org.ting.spring.service.UserService;
import org.ting.spring.utils.Online;

import java.security.Principal;

/**
 * web socket 断开连接监听器
 */
@Component
@Slf4j
public class WebSocketDisconnectListener implements ApplicationListener<SessionDisconnectEvent> {

    @Autowired
    private UserService userService;

    @Autowired
    private SimpMessagingTemplate messageTemplate;

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Principal principal = event.getUser();
        log.info("client sessionId : {} name : {} disconnect ....",event.getSessionId(),principal.getName());
        if (principal != null){ //已经认证过的用户
            User user = userService.findByEmail(principal.getName());
            Online.remove(user);
            messageTemplate.convertAndSend("/topic/user.list",Online.onlineUsers());
        }
    }
}
