package org.ting.spring.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.ting.spring.dao.AuthorityDao;
import org.ting.spring.dao.UserDao;
import org.ting.spring.model.Authority;
import org.ting.spring.model.User;

@Component
public class CustomerUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private AuthorityDao AuthorityDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    	User user = userDao.findByEmail(username);
    	if(user == null)
    		throw new UsernameNotFoundException(username + " is not exists....");
    	Authority authority = AuthorityDao.findOne(username);
    	List<GrantedAuthority> list = new ArrayList<>();
    	list.add(new SimpleGrantedAuthority(authority.getAuthority()));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				user.isEnable(),true,true,true,list);
    }
}
