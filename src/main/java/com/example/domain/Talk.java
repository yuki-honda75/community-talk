package com.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Talk {
	private Integer talkId;
	private String context;
	private Integer groupId;
	private Profile profile;
}
