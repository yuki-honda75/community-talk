package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Profile;
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
	public String createProfile(@RequestBody Profile profile) {
		profileService.insert(profile);
		
		return "成功";
	}
}
