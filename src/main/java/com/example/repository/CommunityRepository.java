package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;
import com.example.domain.Community;
import com.example.domain.Hobby;

@Repository
public class CommunityRepository {
	private static final RowMapper<Community> COMMUNITY_ROW_MAPPER = (rs, i) -> {
		Community community = new Community();
		community.setComId(rs.getInt("com_id"));
		community.setName(rs.getString("community_name"));
		
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		
		Hobby hobby = new Hobby();
		hobby.setHobbyId(rs.getInt("hobby_id"));
		hobby.setName(rs.getString("hobby_name"));
		
		category.setHobby(hobby);
		community.setCategory(category);
		
		return community;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Community> findAll() {
		String sql = "select com_id, co.name as community_name, ca.category_id as category_id,"
				+ " ca.name as category_name, h.hobby_id as hobby_id, h.name as hobby_name"
				+ " from communities as co"
				+ " join categories as ca on co.category_id=ca.category_id"
				+ " join hobbies as h on ca.hobby_id=h.hobby_id";
		
		List<Community> communityList = template.query(sql, COMMUNITY_ROW_MAPPER);
		
		return communityList;
	}
}
