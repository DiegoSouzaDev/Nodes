package com.treasy.challenge.dto;

import lombok.Data;

@Data
public class NodeChildDTO {
	
	private Long id;

	private String code;

	private String description;
	
	private String detail;

	private Long parentId;
	
	private Boolean hasChildren;

}
