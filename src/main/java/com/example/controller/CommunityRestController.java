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
	public Map<String, List<Community>> getCommunity(String userId) {
		Map<String, List<Community>> map = new HashMap<>();
		List<Community> joinedCommunityList = communityService.findByJoined(userId);
		List<Community> recommendedCommunityList = communityService.findByRecommendation(userId);
		
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
	public Map<String, String> create(@RequestBody PostCommunity postCommunity) {
		Map<String, String> message = new HashMap<>();
		List<Community> communityList = communityService.findByName(postCommunity.getName());
		if (!communityList.isEmpty()) {
			message.put("error", "そのコミュニティ名はすでに使われています");
			return message;
		}
		communityService.insert(postCommunity);
		message.put("message", "作成しました");
		return message;
	}
}
