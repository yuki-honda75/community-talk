package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.Hobby;
import com.example.repository.HobbyRepository;

@Service
public class HobbyService {
	@Autowired
	private HobbyRepository hobbyRepository;
	
	public List<Hobby> findAll() {
		return hobbyRepository.findAll();
	}
}
