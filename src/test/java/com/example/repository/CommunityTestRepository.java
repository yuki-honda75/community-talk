package com.example.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.domain.Community;

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
		assertEquals("サンプル1", communityList.get(0).getName());
	}
}
