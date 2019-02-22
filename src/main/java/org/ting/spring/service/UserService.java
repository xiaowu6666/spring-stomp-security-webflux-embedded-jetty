package org.ting.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.ting.spring.dao.AuthorityDao;
import org.ting.spring.dao.UserDao;
import org.ting.spring.model.Authority;
import org.ting.spring.model.User;

import java.util.List;

import static org.ting.spring.dao.UserDao.randomId;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthorityDao authorityDao;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String AVATAR = "/static/images/avatar/man.png";

    private final static String ROLE = "ROLE_USER";

    @Transactional
    public void insert(User user){
        User email = findByEmail(user.getEmail());
        if (email != null) //邮箱不能重复
            return;
        User uname = findByUname(user.getUsername());
        if (uname != null) //昵称不能重复
            return;
        user.setEnable(true);
        if(user.getAvatar() == null)
        user.setAvatar(AVATAR);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Authority authority = new Authority();
        authority.setEmail(user.getEmail());
        authority.setAuthority(ROLE);
        if (user.getId() == 0)
            user.setId(randomId());
        authorityDao.insert(authority);
        userDao.insert(user);
    }

    @Transactional
    public void update(User user){
        userDao.update(user);
    }

    public List<User> findAll(){
        return userDao.findAllUser();
    }

    public User findOne(int id){
        return userDao.findOne(id);
    }

    public User findByEmail(String email){ return userDao.findByEmail(email);}

    public User findByUname(String username){
        return userDao.findByUname(username);
    }
}
