package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Community;
import com.example.repository.CommunityRepository;

@Service
public class CommunityService {
	@Autowired
	private CommunityRepository communityRepository;
	
	public List<Community> findAll() {
		return communityRepository.findAll();
	}
}
