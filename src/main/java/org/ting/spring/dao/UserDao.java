package org.ting.spring.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.ting.spring.model.User;

import java.util.List;
import java.util.Random;

@Repository
public class UserDao {

    private final static String SELECT_USER = "select id,username,email,enable,password,avatar from users ";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(User user){
        String sql = "insert into users(id,username,email,password,enable,avatar) values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql,user.getId(),user.getUsername(),user.getEmail(),
                user.getPassword(),user.isEnable(),user.getAvatar());
    }

    public void update(User user){
        String sql = "update users set username=?,avatar=? where id=?";
        jdbcTemplate.update(sql,user.getUsername(),user.getAvatar(),user.getId());
    }

    public User findOne(int id){
        User user = jdbcTemplate.queryForObject(SELECT_USER+"where id = ?",
                new Object[]{id}, userRowMapper());
        return user;
    }

    public User findByUname(String username){
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(SELECT_USER + "where username = ?",
                    new Object[]{username}, userRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return user;
    }
    
    public User findByEmail(String email) {
    	User user = null;
    	try {
    	user = jdbcTemplate.queryForObject(SELECT_USER+"where email=?"
    			, new Object[] {email},userRowMapper());
    	}catch (EmptyResultDataAccessException  e) {
			return null;
		}
    	return user;
    }


    public List<User> findAllUser(){
        List<User> list = jdbcTemplate.query(SELECT_USER, userRowMapper());
        return list;
    }

    public RowMapper<User> userRowMapper(){
        return  (rs,index) -> {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            user.setEnable(rs.getBoolean("enable"));
            user.setAvatar(rs.getString("avatar"));
            return user;
        };
    }

    public static int randomId(){
        Random random = new Random();
        return random.nextInt(1000000000);
    }
}
