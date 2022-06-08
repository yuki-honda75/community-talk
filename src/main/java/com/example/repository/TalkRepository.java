package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Profile;
import com.example.domain.Talk;
import com.example.domain.PostTalk;

@Repository
public class TalkRepository {
	private static final RowMapper<Talk> TALK_PROFILE_ROW_MAPPER = (rs,i) -> {
		Talk talk = new Talk();
		talk.setTalkId(rs.getInt("talk_id"));
		talk.setContext(rs.getString("context"));
		talk.setGroupId(rs.getInt("group_id"));
		
		Profile profile = new Profile();
		profile.setProfileId(rs.getInt("profile_id"));
		profile.setUserId(rs.getString("user_id"));
		profile.setName(rs.getString("name"));
		profile.setProfession(rs.getString("profession"));
		profile.setIconImg(rs.getString("icon_img"));
		talk.setProfile(profile);
		
		return talk;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Talk> findByGroupId(Integer groupId) {
		String sql = "SELECT talk_id,context,group_id,profile_id,t.user_id as user_id,name,profession,icon_img"
				+ " FROM talks as t join profiles as p on t.user_id=p.user_id"
				+ " WHERE group_id=:groupId"
				+ " order by talk_id";
		SqlParameterSource param = new MapSqlParameterSource().addValue("groupId", groupId);
		
		List<Talk> talkList = template.query(sql, param, TALK_PROFILE_ROW_MAPPER);
		return talkList;
	}
	
	public void insert(PostTalk postTalk) {
		String sql = "insert into talks (context, group_id, user_id) values"
				+ " (:context, :groupId, :userId)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("context", postTalk.getContext())
				.addValue("groupId", postTalk.getGroupId())
				.addValue("userId", postTalk.getUserId());
		
		template.update(sql, param);
	}
}
