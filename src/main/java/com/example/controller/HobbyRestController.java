package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Hobby;
import com.example.service.HobbyService;

@RestController
@RequestMapping("/hobbyapi")
public class HobbyRestController {
	@Autowired
	private HobbyService hobbyService;
	
	@RequestMapping(value = "/all", method = RequestMethod.GET)
	public Map<String, List<Hobby>> getHobby() {
		Map<String, List<Hobby>> map = new HashMap<>();
		List<Hobby> hobbyList = hobbyService.findAll();
		
		map.put("hobbyList", hobbyList);
		return map;
	}
}
