package org.ting.spring.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.ting.spring.dao.UserDao;
import org.ting.spring.model.User;

@Component
public class SecurityAuth {

    @Autowired
    private UserDao userDao;

    public User getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            String username = authentication.getName();
            User user = userDao.findByEmail(username);
            return user;
        }
        return null;
    }
}
