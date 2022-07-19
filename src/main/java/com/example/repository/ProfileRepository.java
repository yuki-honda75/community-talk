package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.PostProfile;
import com.example.domain.Profile;

@Repository
public class ProfileRepository {
	private static final RowMapper<Profile> PROFILE_ROW_MAPPER = new BeanPropertyRowMapper<>(Profile.class);
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * ユーザーIDからプロフィール情報を取得
	 * 
	 * @param userId
	 * @return
	 */
	public Profile findByUserId(String userId) {
		String sql = "select p.profile_id, user_id, name, profession, icon_img, ph.hobby_id"
				+ " from profiles p join profiles_between_hobbies as ph"
				+ " on p.profile_id = ph.profile_id"
				+ " where user_id=:userId";
		
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
	@Transactional
	public void insert(PostProfile postProfile) {
		String sql = "insert into profiles (user_id, name, profession, icon_img)"
				+ " values (:userId, :name, :profession, :iconImg) returning profile_id";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", postProfile.getUserId())
				.addValue("name", postProfile.getName())
				.addValue("profession", postProfile.getProfession())
				.addValue("iconImg", postProfile.getIconImg());
		
		int profileId = template.queryForObject(sql, param, Integer.class);
		insertHobby(postProfile.getHobbyId(), profileId);
	}
	
	public void insertHobby(List<Integer> hobbyId, Integer profileId) {
		StringBuilder sql = 
				new StringBuilder("insert into profiles_between_hobbies (profile_id, hobby_id) values");
		int i = 0;
		MapSqlParameterSource param = new MapSqlParameterSource();
		for (Integer id : hobbyId) {
			sql.append(" (:profileId, :hobbyId" + i + ")");
			param.addValue("hobbyId" + i, id);
			if (hobbyId.size()-1 != i) {
				sql.append(",");
			}
			i++;
		}
		param.addValue("profileId", profileId);
		template.update(sql.toString(), param);
	}
}
