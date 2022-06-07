package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Profile;

@Repository
public class ProfileRepository {
	private static final RowMapper<Profile> PROFILE_ROW_MAPPER = new BeanPropertyRowMapper<>();
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * ユーザーIDからプロフィール情報を取得
	 * 
	 * @param userId
	 * @return
	 */
	public Profile findByUserId(String userId) {
		String sql = "select profile_id, user_id, name, profession, icon_img"
				+ " from profiles where user_id=:userId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Profile> profileList = template.query(sql, param, PROFILE_ROW_MAPPER);
		if (profileList.isEmpty()) {
			return null;
		}
		
		return profileList.get(0);
	}
	
	/**
	 * プロフィール情報を登録
	 * 
	 * @param profile
	 */
	public void insert(Profile profile) {
		String sql = "insert into profiles (user_id, name, profession, icon_img)"
				+ " values (:userId, :name, :profession, :iconImg)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", profile.getUserId())
				.addValue("name", profile.getName())
				.addValue("profession", profile.getProfession())
				.addValue("iconImg", profile.getIconImg());
		
		template.update(sql, param);
	}
}
