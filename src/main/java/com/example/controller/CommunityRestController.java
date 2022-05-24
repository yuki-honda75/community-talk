package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Community;
import com.example.service.CommunityService;

@RestController
@RequestMapping("/communityapi")
public class CommunityRestController {
	@Autowired
	private CommunityService communityService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, List<Community>> getCommunity() {
		Map<String, List<Community>> map = new HashMap<>();
		List<Community> communityList = communityService.findAll();
		
		map.put("communityList", communityList);
		
		return map;
		
	}
}
