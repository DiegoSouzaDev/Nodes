package com.treasy.challenge.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.treasy.challenge.model.Node;

@RestController
@EnableAutoConfiguration
@RequestMapping("/saas")
public class RestAppController {
	
	@RequestMapping(value = "/hi", method = RequestMethod.GET)
	public String hi() {
		return "hello there";
	}
	
	@RequestMapping(value = "/node", method = RequestMethod.POST)
	public String createNode() {
		return "Create";
	}

	@RequestMapping(value = "/node", method = RequestMethod.PUT)
	public String updateNode() {
		return "Update ";
	}
	
	@RequestMapping(value = "/node", method = RequestMethod.GET, produces = "application/json")
	public List<Node> findAllNodes() {

		return createATree();
	}

	@RequestMapping(value = "/node/{parendId}", method = RequestMethod.GET)
	public String findNodesByParentId(@RequestParam("parentId") int parentId) {
		return "ID" + parentId;
	}

	private List<Node> createATree() {
		final List<Node> tree = new ArrayList<>();
		final List<Node> children1 = new ArrayList<>();
		final List<Node> children2 = new ArrayList<>();

		final Node node1 = new Node();
		node1.setId(1);
		node1.setCode(111111);
		node1.setDescription("primeiro nó");
		node1.setDetail("é o primeiro nó cadastrado");
		node1.setParentId(0);

		final Node node2 = new Node();
		node2.setId(2);
		node2.setCode(222222);
		node2.setDescription("pertence a primeira arvore");
		node2.setDetail("filho do primeiro nó");
		node2.setParentId(1);
		
		final Node node3 = new Node();
		node3.setId(3);
		node3.setCode(333333);
		node3.setDescription("pertence a primeira arvore");
		node3.setDetail("filho do primeiro nó");
		node3.setParentId(1);
		
		final Node node4 = new Node();
		node4.setId(4);
		node4.setCode(444444);
		node4.setDescription("pertence a primeira arvore");
		node4.setDetail("filho do primeiro nó");
		node4.setParentId(2);
		
		final Node node5 = new Node();
		node5.setId(5);
		node5.setCode(555555);
		node5.setDescription("pertence a primeira arvore");
		node5.setDetail("filho do primeiro nó");
		node5.setParentId(2);

		children2.add(node4);
		children2.add(node5);
		node2.setChildren(children2);
		
		children1.add(node2);
		children1.add(node3);

		node1.setChildren(children1);

		tree.add(node1);
		
		return tree;
	}

}