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
		group.setUserId(rs.getInt("user_id"));
		
		return group;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	public List<Group> findByComId(Integer comId) {
		String sql = "SELECT g.group_id as group_id,name,user_id FROM groups as g"
				+ " LEFT OUTER JOIN groups_between_users as gu ON g.group_id=gu.group_id"
				+ " WHERE com_id=:comId";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("comId", comId);
		List<Group> groupliList = template.query(sql, param, GROUP_ROW_MAPPER);
		
		return groupliList;
	}
	
	public void insert(PostGroup postGroup) {
		String sql = "insert into groups (name, com_id) values"
				+ " (:name, :comId)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", postGroup.getName())
				.addValue("comId", postGroup.getComId());
		
		template.update(sql, param);
	}
}
