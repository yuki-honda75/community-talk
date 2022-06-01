package com.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Group {
	private Integer groupId;
	private String name;
	private Integer userId;
}
