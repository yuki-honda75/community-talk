package com.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Profile {
	private Integer profileId;
	private Integer userId;
	private String name;
	private String profession;
	private String iconImg;
}
