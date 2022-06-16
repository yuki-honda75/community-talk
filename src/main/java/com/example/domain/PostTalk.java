package com.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostTalk {
	private String context;
	private Integer groupId;
	private String userId;
}
