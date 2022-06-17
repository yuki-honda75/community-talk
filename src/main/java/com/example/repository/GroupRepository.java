package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Group;
import com.example.domain.PostGroup;

@Repository
public class GroupRepository {
	private static final RowMapper<Group> GROUP_ROW_MAPPER = (rs,i) -> {
		Group group = new Group();
		group.setGroupId(rs.getInt("group_id"));
		group.setName(rs.getString("name"));
		
		return group;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * コミュニティごとのトークグループを取得
	 * 
	 * @param comId
	 * @return
	 */
	public List<Group> findByComId(Integer comId) {
		String sql = "SELECT group_id,name FROM groups"
				+ " WHERE com_id=:comId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("comId", comId);
		List<Group> groupliList = template.query(sql, param, GROUP_ROW_MAPPER);
		
		return groupliList;
	}
	
	/**
	 * グループを作成
	 * 
	 * @param postGroup
	 */
	public void insert(PostGroup postGroup) {
		String sql = "insert into groups (name, com_id) values"
				+ " (:name, :comId)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", postGroup.getName())
				.addValue("comId", postGroup.getComId());
		
		template.update(sql, param);
	}
	
	/**
	 * トークグループに参加する処理
	 * 
	 * @param userId
	 * @param comId
	 */
	public void insertGroupBtwUser(String userId, Integer comId) {
		String sql = "insert into groups_between_users (user_id, com_id) values"
				+ " (:userId, :comId)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("comId", comId);
		
		template.update(sql, param);
	}
}
