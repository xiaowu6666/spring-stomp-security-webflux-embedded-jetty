package org.ting.spring.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.security.config.annotation.web.socket.AbstractSecurityWebSocketMessageBrokerConfigurer;

@Configuration
public class SocketSecurityConfig extends AbstractSecurityWebSocketMessageBrokerConfigurer {


    @Override
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) {
       messages.simpDestMatchers("/user/**").authenticated()//认证所有user 链接
       .anyMessage().permitAll();
    }

    //允许跨域  不然会出现 Could not verify the provided CSRF token because your session was not found 异常
    @Override
    protected boolean sameOriginDisabled() {
        return true;
    }
}
