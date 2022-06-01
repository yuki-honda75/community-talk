package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Group;
import com.example.service.GroupService;

@RestController
@RequestMapping("/talkapi")
public class TalkController {
	@Autowired
	private GroupService groupService;
	
	@RequestMapping(value = "/getgroup", method = RequestMethod.GET)
	public Map<String, List<Group>> getGroupList(Integer comId) {
		Map<String, List<Group>> map = new HashMap<>();
		List<Group> groupList = groupService.findByComId(comId);
		
		map.put("groupList", groupList);
		return map;
	}
}
