package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.PostProfile;
import com.example.domain.Profile;
import com.example.domain.UpdateProfile;

@Repository
public class ProfileRepository {
	private static final ResultSetExtractor<List<Profile>> PROFILE_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Profile> profileList = new ArrayList<>();
		List<Integer> hobbyIdList = null;
		int beforeId = 0;
		
		while(rs.next()) {
			int id = rs.getInt("profile_id");
			if (beforeId != id) {
				Profile profile = new Profile();
				profile.setProfileId(id);
				profile.setUserId(rs.getString("user_id"));
				profile.setName(rs.getString("name"));
				profile.setProfession(rs.getString("profession"));
				profile.setIconImg(rs.getString("icon_img"));
				hobbyIdList = new ArrayList<>();
				profile.setHobbyId(hobbyIdList);
				
				profileList.add(profile);
				
				beforeId = id;
			}
			
			Integer hobbyId = rs.getInt("hobby_id");
			hobbyIdList.add(hobbyId);
		}
			
		
		return profileList;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * ユーザーIDからプロフィール情報を取得
	 * 
	 * @param userId
	 * @return
	 */
	public Profile findByUserId(String userId) {
		String sql = "select p.profile_id, user_id, name, profession, icon_img, hobby_id"
				+ " from profiles p join profiles_between_hobbies as ph"
				+ " on p.profile_id = ph.profile_id"
				+ " where user_id=:userId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		List<Profile> profileList = template.query(sql, param, PROFILE_RESULT_SET_EXTRACTOR);
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
	
	/**
	 * 交差テーブルに趣味を登録
	 * 
	 * @param hobbyId
	 * @param profileId
	 */
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
	
	/**
	 * プロフィール情報のアップデート
	 * 
	 * @param updateProfile
	 */
	public void update(UpdateProfile updateProfile) {
		String sql = "update profiles"
				+ " set user_id=:userId, name=:name, profession=:profession, icon_img=:iconImg"
				+ " where profile_id=:profileId";
		Integer profileId = updateProfile.getProfileId();
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", updateProfile.getUserId())
				.addValue("name", updateProfile.getName())
				.addValue("profession", updateProfile.getProfession())
				.addValue("iconImg", updateProfile.getIconImg())
				.addValue("profileId", profileId);
		
		template.update(sql, param);
		deleteHobby(profileId);
		insertHobby(updateProfile.getHobbyId(), profileId);
	}
	
	/**
	 * 中間テーブルからプロフィールに紐づくデータを削除
	 * 
	 * @param profileId
	 */
	public void deleteHobby(Integer profileId) {
		String sql = "delete from profiles_between_hobbies Where profile_id=:profileId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("profileId", profileId);
		
		template.update(sql, param);
	}
}
