package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Profile;
import com.example.repository.ProfileRepository;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepository profileRepository;
	
	public Profile findByUserId(String userId) {
		return profileRepository.findByUserId(userId);
	}
	
	public void insert(Profile profile) {
		profileRepository.insert(profile);
	}
}
