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

import com.example.domain.Category;
import com.example.domain.ComBtwUser;
import com.example.domain.Community;
import com.example.domain.Hobby;
import com.example.domain.PostCommunity;

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
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	/**
	 * 名前からコミュニティを検索する
	 * 
	 * @return
	 */
	public List<Community> findByName(String name) {
		String sql = "select com_id, co.name as community_name, ca.category_id as category_id,"
				+ " ca.name as category_name, h.hobby_id as hobby_id, h.name as hobby_name"
				+ " from communities as co"
				+ " join categories as ca on co.category_id=ca.category_id"
				+ " join hobbies as h on ca.hobby_id=h.hobby_id"
				+ " where co.name=:name";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", name);
		
		List<Community> communityList = template.query(sql, param, COMMUNITY_ROW_MAPPER);
		
		return communityList;
	}
	
	/**
	 * 参加済みのコミュニティを取得する
	 * 
	 * @param userId
	 * @return
	 */
	public List<Community> findByJoined(String userId) {
		String sql = "SELECT co.com_id as com_id, co.name as community_name, ca.category_id as category_id,"
				+ " ca.name as category_name, h.hobby_id as hobby_id, h.name as hobby_name FROM communities as co"
				+ " JOIN communities_between_users as cu ON co.com_id=cu.com_id"
				+ " join categories as ca on co.category_id=ca.category_id"
				+ " join hobbies as h on ca.hobby_id=h.hobby_id"
				+ " WHERE user_id=:userId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		
		List<Community> communityList = template.query(sql, param, COMMUNITY_ROW_MAPPER);
		return communityList;
	}
	
	/**
	 * おすすめのコミュニティを取得する
	 * 
	 * @return
	 */
	public List<Community> findByRecommendation(String userId) {
		String sql = "SELECT co.com_id as com_id,co.name as community_name,ca.category_id as category_id,"
				+ " ca.name as category_name,h.hobby_id as hobby_id,h.name as hobby_name FROM communities as co"
				+ " JOIN categories as ca on co.category_id=ca.category_id"
				+ " JOIN (SELECT h.hobby_id,h.name FROM profiles as p"
				+ " JOIN profiles_between_hobbies as ph ON p.profile_id=ph.profile_id"
				+ " JOIN hobbies as h ON ph.hobby_id=h.hobby_id"
				+ " WHERE p.user_id=:userId) as h ON ca.hobby_id=h.hobby_id"
				+ " WHERE com_id not in "
				+ " (SELECT com_id FROM communities_between_users where user_id=:userId)";
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		
		List<Community> communityList = template.query(sql, param, COMMUNITY_ROW_MAPPER);
		
		return communityList;
	}
	
	/**
	 * 趣味IDから取得する
	 * 
	 * @param hobbyId
	 * @return
	 */
	public List<Community> findByHobby(Integer hobbyId) {
		String sql = "select com_id, co.name as community_name, ca.category_id as category_id,"
				+ " ca.name as category_name, h.hobby_id as hobby_id, h.name as hobby_name"
				+ " from communities as co"
				+ " join categories as ca on co.category_id=ca.category_id"
				+ " join hobbies as h on ca.hobby_id=h.hobby_id"
				+ " where h.hobby_id=:hobbyId";
		SqlParameterSource param = new MapSqlParameterSource().addValue("hobbyId", hobbyId);
		
		List<Community> communityList = template.query(sql, param, COMMUNITY_ROW_MAPPER);
		return communityList;
	}
	
	@Transactional
	public void insert(PostCommunity postCommunity) {
		//カテゴリーがあるかを確認
		Category category = categoryRepository
				.findByNameAndHobbyId(postCommunity.getCategory(), postCommunity.getHobbyId());
		int categoryId = 0;
		if (category != null) {
			//あればそのカテゴリのIdを取得
			categoryId = category.getCategoryId();
		} else {
			//なければ新しくカテゴリを登録してIdを取得
			String sql = "insert into categories (name, hobby_id)"
					+ " values (:name, :hobbyId) returning category_id";
			SqlParameterSource param = new MapSqlParameterSource()
					.addValue("name", postCommunity.getCategory())
					.addValue("hobbyId", postCommunity.getHobbyId());
			categoryId = template.queryForObject(sql, param, Integer.class);
		}
		String sql = "insert into communities (name, category_id)"
				+ " values (:name, :categoryId)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("name", postCommunity.getName())
				.addValue("categoryId", categoryId);
		
		template.update(sql, param);
	}
	
	/**
	 * コミュニティに参加する処理
	 * 
	 * @param userId
	 * @param comId
	 */
	public void insertComBtwUser(String userId, Integer comId) {
		String sql = "insert into communities_between_users (user_id, com_id) values"
				+ " (:userId, :comId)";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("comId", comId);
		
		template.update(sql, param);
	}
	
	/**
	 * 参加を解除
	 * 
	 * @param userId
	 * @param comId
	 */
	public void deleteComBtwUser(String userId, Integer comId) {
		String sql = "delete from communities_between_users"
				+ " where user_id=:userId and com_id=:comId";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("comId", comId);
		
		template.update(sql, param);
	}
	
	/**
	 * 参加状況のチェック
	 * 
	 * @param userId
	 * @param comId
	 * @return
	 */
	public boolean checkComBtwUser(String userId, Integer comId) {
		String sql = "select * from communities_between_users"
				+ " where user_id=:userId and com_id=:comId";
		SqlParameterSource param = new MapSqlParameterSource()
				.addValue("userId", userId)
				.addValue("comId", comId);
		RowMapper<ComBtwUser> mapper = new BeanPropertyRowMapper<>(ComBtwUser.class);
		List<ComBtwUser> list = template.query(sql, param, mapper);
		if (list.isEmpty()) {
			//不参加
			return false;
		}
		//参加済み
		return true;
		
	}
}
