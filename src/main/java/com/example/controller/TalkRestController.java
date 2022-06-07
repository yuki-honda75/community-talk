package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Group;
import com.example.domain.PostGroup;
import com.example.domain.PostTalk;
import com.example.domain.Talk;
import com.example.service.GroupService;
import com.example.service.TalkService;

@RestController
@RequestMapping("/talkapi")
public class TalkRestController {
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private TalkService talkService;
	
	@RequestMapping(value = "/getgroup", method = RequestMethod.GET)
	public Map<String, List<Group>> getGroupList(Integer comId) {
		Map<String, List<Group>> map = new HashMap<>();
		List<Group> groupList = groupService.findByComId(comId);
		
		map.put("groupList", groupList);
		return map;
	}
	
	@RequestMapping(value = "/gettalk", method = RequestMethod.GET)
	public Map<String, List<Talk>> getTalkList(Integer groupId) {
		Map<String, List<Talk>> map = new HashMap<>();
		List<Talk> talkList = talkService.findByGroupId(groupId);
		
		map.put("talkList", talkList);
		return map;
	}
	
	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String create(@RequestBody PostTalk postTalk) {
		talkService.insert(postTalk);
		
		return "成功";
	}
	
	@RequestMapping(value = "/creategroup", method = RequestMethod.POST)
	public String createGroup(@RequestBody PostGroup postGroup) {
		groupService.insert(postGroup);
		
		return "成功";
	}
}
