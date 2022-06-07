package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Group;
import com.example.domain.PostGroup;
import com.example.repository.GroupRepository;

@Service
public class GroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	public List<Group> findByComId(Integer comId) {
		return groupRepository.findByComId(comId);
	}
	
	public void insert(PostGroup postGroup) {
		groupRepository.insert(postGroup);
	}
}
