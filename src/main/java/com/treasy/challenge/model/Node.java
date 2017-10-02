package com.treasy.challenge.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Node {
	
	@Id
	@GeneratedValue
	private Integer id;

	private String code;

	private String description;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	private Node parent;

	private String detail;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	private List<Node> children;
}
