package com.treasy.challenge.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Node {
	
	@Id
	@GeneratedValue
	private Long id;

	private String code;

	private String description;
	
	private String detail;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "parentId", referencedColumnName = "id")
	private Node parent;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "parent")
	private List<Node> children;

}
