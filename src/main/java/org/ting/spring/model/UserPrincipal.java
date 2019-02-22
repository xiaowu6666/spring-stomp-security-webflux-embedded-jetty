package org.ting.spring.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class UserPrincipal implements OAuth2User,UserDetails {

    private long id;

    private String name;

    private String password;

    private boolean enable;

    private Collection<? extends GrantedAuthority> authorities;

    private Map<String,Object> attributes;

    UserPrincipal(long id,String name,String password,boolean enable,Collection<? extends GrantedAuthority> authorities){
        this.id = id;
        this.name = name;
        this.password = password;
        this.authorities = authorities;
        this.enable = enable;
    }

    public static UserPrincipal create(User user){
        return new UserPrincipal(user.getId(),user.getEmail()
                ,user.getPassword(),user.isEnable(),Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public static UserPrincipal create(User user, Map<String, Object> attributes) {
        UserPrincipal userPrincipal = UserPrincipal.create(user);
        userPrincipal.attributes = attributes;
        return userPrincipal;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enable;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attributes;
    }

    @Override
    public String getName() {
        return String.valueOf(this.id);
    }
}
