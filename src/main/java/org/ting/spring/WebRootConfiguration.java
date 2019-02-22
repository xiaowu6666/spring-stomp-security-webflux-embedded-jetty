package org.ting.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.ting.spring.config.WebMvcConfiguration;
import org.ting.spring.config.security.SecurityConfig;
import org.ting.spring.config.WebSocketStompConfig;

@Configuration
@ComponentScan(basePackages = "org.ting.spring",
        excludeFilters = {@ComponentScan.Filter(value =
                {Controller.class,RestController.class,ControllerAdvice.class})})
@Import({WebMvcConfiguration.class,WebSocketStompConfig.class,SecurityConfig.class})
public class WebRootConfiguration {

    @Autowired
    private DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate template = new JdbcTemplate(dataSource);
        return template;
    }


}
