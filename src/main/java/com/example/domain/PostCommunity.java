package com.example.domain;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class PostCommunity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private Integer hobbyId;
	private String category;

}
