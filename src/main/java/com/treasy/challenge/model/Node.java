package com.treasy.challenge.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Data
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Node {
	
	@Id
	@GeneratedValue
	private Long id;

	private String code;

	private String description;
	
	private String detail;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	private Node parent;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	private List<Node> children;

}
