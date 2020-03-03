package com.example.pgsample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class AppMain {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void startMain() {
		jdbcTemplate.query("select now() ", rs->{
			System.out.println(rs.getTimestamp(1));
		});
		
	}
}
