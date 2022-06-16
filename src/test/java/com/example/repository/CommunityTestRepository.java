package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.Category;
import com.example.domain.Community;
import com.example.domain.Hobby;
import com.example.domain.PostCommunity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
@ActiveProfiles("test")
public class CommunityTestRepository {
	@Autowired
	private CommunityRepository communityRepository;
	
	@BeforeAll
	public static void initAll() {
		log.info("=====start=====");
	}
	
	@AfterAll
	public static void tearDownAll() {
		log.info("=====finish=====");
	}
	
	@Test
	public void findByHobbyId() {
		List<Community> communityList = communityRepository.findByHobby(5);
		Community community = new Community();
		community.setComId(1);
		community.setName("サンプル1");
		
		Category category = new Category();
		category.setCategoryId(1);
		category.setName("J-POP");
		
		Hobby hobby = new Hobby();
		hobby.setHobbyId(5);
		hobby.setName("音楽");
		
		category.setHobby(hobby);
		community.setCategory(category);
		List<Community> sampleList = Arrays.asList(community);
		
		assertEquals("サンプル1", communityList.get(0).getName());
		assertIterableEquals(sampleList, communityList);
	}
	
	@Test
	public void insertTest() {
		PostCommunity postCommunity = new PostCommunity();
		postCommunity.setName("サンプル4");
		postCommunity.setCategory("sample");
		postCommunity.setHobbyId(1);
		communityRepository.insert(postCommunity);
		
		List<Community> communityList = communityRepository.findByHobby(1);
		
		assertEquals(2, communityList.size());
		assertEquals("サンプル2", communityList.get(1).getName());
	}
}
