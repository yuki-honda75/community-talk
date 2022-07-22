package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.PostProfile;
import com.example.domain.Profile;
import com.example.domain.UpdateProfile;
import com.example.service.ProfileService;

@RestController
@RequestMapping("/profileapi")
public class ProfileRestController {
	@Autowired
	private ProfileService profileService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Map<String, Profile> getProfile(String userId) {
		Map<String, Profile> map = new HashMap<>();
		Profile profile = profileService.findByUserId(userId);
		
		map.put("profile", profile);
		return map;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String createProfile(@RequestBody PostProfile postProfile) {
		profileService.insert(postProfile);
		
		return "成功";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateProfile(@RequestBody UpdateProfile updateProfile) {
		System.out.println(updateProfile);
		profileService.update(updateProfile);
		
		return "成功";
	}
}
