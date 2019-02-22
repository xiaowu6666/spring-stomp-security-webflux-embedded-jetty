package org.ting.spring.config.security;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;

public class WebSecurityApplicationInitialer extends AbstractSecurityWebApplicationInitializer {

    @Override
    protected boolean enableHttpSessionEventPublisher() {
        return true;
    }
}
