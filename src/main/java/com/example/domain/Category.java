package com.example.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class Category {
	private Integer categoryId;
	private String name;
	private Hobby hobby;
 }
