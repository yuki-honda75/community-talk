package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Hobby;

@Repository
public class HobbyRepository {
	private static final RowMapper<Hobby> HOBBY_ROW_MAPPER = new BeanPropertyRowMapper<>(Hobby.class);
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Hobby> findAll() {
		String sql = "select hobby_id,name from hobbies";
		List<Hobby> hobbyList = template.query(sql, HOBBY_ROW_MAPPER);
		
		return hobbyList;
	}
}
