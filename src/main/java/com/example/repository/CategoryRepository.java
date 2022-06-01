package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Category;
import com.example.domain.Hobby;

@Repository
public class CategoryRepository {
	private static final RowMapper<Category> CATEGORY_ROW_MAPPER = (rs,i) -> {
		Category category = new Category();
		category.setCategoryId(rs.getInt("category_id"));
		category.setName(rs.getString("category_name"));
		
		Hobby hobby = new Hobby();
		hobby.setHobbyId(rs.getInt("hobby_id"));
		hobby.setName(rs.getString("hobby_name"));
		category.setHobby(hobby);
		
		return category;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * カテゴリ名のチェック
	 * 
	 * @param name
	 * @return
	 */
	public Category findByNameAndHobbyId(String name, Integer hobbyId) {
		String sql = "select category_id,c.name as category_name,c.hobby_id,h.name as hobby_name from categories as c"
				+ " join hobbies as h on c.hobby_id=h.hobby_id"
				+ " where c.name=:name and h.hobby_id=:hobbyId";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", name)
				.addValue("hobbyId", hobbyId);
		
		List<Category> categoryList = template.query(sql, param, CATEGORY_ROW_MAPPER);
		if (categoryList.isEmpty()) {
			return null;
		}
		
		return categoryList.get(0);
	}
}
