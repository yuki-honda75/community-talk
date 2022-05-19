package com.example.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloWorldRestController {
	//@CrossOrigin
	@RequestMapping(value = "/hello", method = RequestMethod.GET)
	public Map<String, String> hello() {
		Map<String, String> map = new HashMap<>();
		map.put("greeting", "Hello World!");
		return map;
	}
}
