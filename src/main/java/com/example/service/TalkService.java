package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.PostTalk;
import com.example.domain.Talk;
import com.example.repository.TalkRepository;

@Service
public class TalkService {
	@Autowired
	private TalkRepository talkRepository;
	
	public List<Talk> findByGroupId(Integer groupId) {
		return talkRepository.findByGroupId(groupId);
	}
	
	public void insert(PostTalk postTalk) {
		talkRepository.insert(postTalk);
	}
}
