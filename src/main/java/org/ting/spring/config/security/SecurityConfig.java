package org.ting.spring.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.ting.spring.authentication.OAuth2AuthenticationFailureHandler;
import org.ting.spring.authentication.RestAuthFailureHandler;
import org.ting.spring.authentication.RestAuthSuccessHandler;
import org.ting.spring.authentication.RestAuthenticationEntryPoint;
import org.ting.spring.service.CustomOAuth2UserService;
import org.ting.spring.service.CustomerUserDetailService;



@EnableWebSecurity
@PropertySource("classpath:registration.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomerUserDetailService userDetailService;

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    private Environment env;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling()
                .authenticationEntryPoint(entryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/static/html/jetty-chat.html",
                        "/api/user/online", "/api/user/loginuser")
                .authenticated()
                .and()
                .formLogin()
                .successHandler(successHandler())
                .failureHandler(failureHandler())
                .loginPage("/static/html/login.html")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/static/html/jetty-chat.html")
                .permitAll()
                .and().csrf().disable()//禁用csrf 因为没有使用模板引擎
                .sessionManagement().maximumSessions(1)
                .sessionRegistry(sessionManager())
                .expiredUrl("/static/html/login.html") //session 失效后，跳转url
                .maxSessionsPreventsLogin(false) //设置true，达到session 最大登录次数后，后面的账户都会登录失败，false 顶号 前面登录账户会被后面顶下线

        ;
        http.oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
	        .defaultSuccessUrl("/static/html/jetty-chat.html")
            .failureHandler(new OAuth2AuthenticationFailureHandler());
        http.logout().logoutUrl("/logout").logoutSuccessUrl("/static/html/login.html"); //注销url，跳转到登录页面
    }

    @Bean
    public AuthenticationEntryPoint entryPoint() {
        RestAuthenticationEntryPoint entryPoint = new RestAuthenticationEntryPoint("/static/html/login.html");
        return entryPoint;
    }

    @Bean
    public SimpleUrlAuthenticationSuccessHandler successHandler() {
        RestAuthSuccessHandler successHandler = new RestAuthSuccessHandler();
        return successHandler;
    }

    @Bean
    public SimpleUrlAuthenticationFailureHandler failureHandler() {
        RestAuthFailureHandler failureHandler = new RestAuthFailureHandler();
        return failureHandler;
    }

    @Bean
    public SessionRegistry sessionManager() {
        return new SessionRegistryImpl();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(githubClientRegstrationRepository()
                ,googleClientRegistrionRepository());
    }

    public ClientRegistration githubClientRegstrationRepository(){
        return CommonOAuth2Provider.GITHUB.getBuilder("github")
                .clientId(env.getProperty("registration.github.client-id"))
                .clientSecret(env.getProperty("registration.github.client-secret"))
                .redirectUriTemplate(env.getProperty("registration.github.redirect-uri-template"))
                .build();
    }

    public ClientRegistration googleClientRegistrionRepository(){
         return CommonOAuth2Provider.GOOGLE.getBuilder("google")
                .clientId(env.getProperty("registration.google.client-id"))
                .clientSecret(env.getProperty("registration.google.client-secret"))
                .redirectUriTemplate(env.getProperty("registration.google.redirect-uri-template"))
                .scope( "profile", "email")
                .build();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }



}
