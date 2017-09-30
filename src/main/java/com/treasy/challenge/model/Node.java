package com.treasy.challenge.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Node {
	
	private Integer id;

	private Integer code;

	private String description;

	private Integer parentId;

	private String detail;
	
	private List<Node> children;
}
