package com.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Community {
	private Integer comId;
	private String name;
	private Category category;
}
