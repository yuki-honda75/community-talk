package com.example.domain;

import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateProfile {
	private Integer profileId;
	private String userId;
	private String name;
	private String profession;
	private String iconImg;
	private List<Integer> hobbyId;
}
