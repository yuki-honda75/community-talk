package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Community;
import com.example.domain.PostCommunity;
import com.example.service.CommunityService;

@RestController
@RequestMapping("/communityapi")
public class CommunityRestController {
	@Autowired
	private CommunityService communityService;
	
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public Map<String, List<Community>> getCommunity() {
		Map<String, List<Community>> map = new HashMap<>();
		List<Community> joinedCommunityList = communityService.findByJoined(1);
		List<Community> recommendedCommunityList = communityService.findByRecommendation(1);
		
		map.put("joined", joinedCommunityList);
		map.put("recommended", recommendedCommunityList);
		
		return map;
		
	}
	
	@RequestMapping(value = "/gethobby", method = RequestMethod.GET)
	public Map<String, List<Community>> getCommunityHobby(Integer hobbyId) {
		Map<String, List<Community>> map = new HashMap<>();
		List<Community> communityList = communityService.findByHobby(hobbyId);
		
		map.put("communityHobbyList", communityList);
		
		return map;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String create(@RequestBody PostCommunity postCommunity) {
		System.out.println(postCommunity);
		
		return "成功";
	}
}
