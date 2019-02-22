package org.ting.spring.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.ting.spring.model.User;
import org.ting.spring.model.UserPrincipal;

import java.util.Map;

@Service
@Slf4j
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        try {
           oAuth2User = processOAuth2User(oAuth2User,userRequest);
        } catch (Exception e) {
            log.error("processOAuth2User error {}",e);
        }
        return oAuth2User;
    }

    private OAuth2User processOAuth2User(OAuth2User oAuth2User,OAuth2UserRequest userRequest) {
        String clientId = userRequest.getClientRegistration().getRegistrationId();
        if (clientId.equalsIgnoreCase("github")) {
            Map<String, Object> map = oAuth2User.getAttributes();
            String login =  map.get("login")+"_oauth_github";
            String name = (String) map.get("name");
            String avatarUrl = (String) map.get("avatar_url");
            User user = userService.findByEmail(login);
            if (user == null) {
                user = new User();
                user.setUsername(name);
                user.setEmail(login);
                user.setAvatar(avatarUrl);
                user.setPassword("123456");
                userService.insert(user);
            }else {
                user.setUsername(name);
                user.setAvatar(avatarUrl);
                userService.update(user);
            }
            return UserPrincipal.create(user, oAuth2User.getAttributes());
        }else if (clientId.equalsIgnoreCase("google")){
            Map<String, Object> result = oAuth2User.getAttributes();
            String email = result.get("email")+"_oauth_google";
            String username = (String) result.get("name");
            String imgUrl = (String) result.get("picture");
            User user = userService.findByEmail(email);
            if (user == null){
                user = new User();
                user.setEmail(email);
                user.setPassword("123456");
                user.setAvatar(imgUrl);
                user.setUsername(username);
                userService.insert(user);
            }else {
                user.setUsername(username);
                user.setAvatar(imgUrl);
                userService.update(user);
            }
            return UserPrincipal.create(user,oAuth2User.getAttributes());
        }
        return null;
    }
}
