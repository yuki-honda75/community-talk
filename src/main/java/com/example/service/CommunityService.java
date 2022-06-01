package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Community;
import com.example.domain.PostCommunity;
import com.example.repository.CommunityRepository;

@Service
public class CommunityService {
	@Autowired
	private CommunityRepository communityRepository;
	
	public List<Community> findByName(String name) {
		return communityRepository.findByName(name);
	}
	
	public List<Community> findByJoined(Integer userId) {
		return communityRepository.findByJoined(userId);
	}
	
	public List<Community> findByRecommendation(Integer userId) {
		return communityRepository.findByRecommendation(userId);
	}
	
	public List<Community> findByHobby(Integer hobbyId) {
		return communityRepository.findByHobby(hobbyId);
	}
	
	public void insert(PostCommunity postCommunity) {
		communityRepository.insert(postCommunity);
	}
}
