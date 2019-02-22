package org.ting.spring.stomp.message;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.ting.spring.model.*;

import java.time.LocalDateTime;

@Controller
@Slf4j
public class StompController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;


    @MessageExceptionHandler
    @SendToUser("/queue.errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @MessageMapping("receive.messgae")
    public void forwardMsg(ChatMessage message){
        log.info("message :  {}",message);
        message.setLocalDateTime(LocalDateTime.now());
        messagingTemplate.convertAndSendToUser(message.getTargetUser().getEmail()
                ,"queue.notification",message);
    }

}
