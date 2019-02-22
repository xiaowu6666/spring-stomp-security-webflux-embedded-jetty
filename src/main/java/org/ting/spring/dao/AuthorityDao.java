package org.ting.spring.dao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.ting.spring.model.Authority;

import static org.ting.spring.dao.UserDao.randomId;

@Repository
public class AuthorityDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private final static String SELECT_AUTHORITY = "select id,email,authority from authorities ";
	
	public Authority findOne(String email) {
		return jdbcTemplate.queryForObject(SELECT_AUTHORITY+"where  email=?",new Object[]{email}, rowMapperAuthority());
	}

	public void insert(Authority authority){
		jdbcTemplate.update("insert into authorities(id,email,authority)values(?,?,?)",
				new Object[]{randomId(),authority.getEmail(),authority.getAuthority()});
	}
	
	public RowMapper<Authority> rowMapperAuthority() {
		return (rs,rowNum) -> {
			Authority auth = new Authority();
			auth.setId(rs.getInt("id"));
			auth.setAuthority(rs.getString("authority"));
			auth.setEmail(rs.getString("email"));
			return auth;
		};
	}
}
