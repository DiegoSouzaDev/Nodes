package com.treasy.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.treasy.challenge.dto.NodeChildDTO;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.service.NodeService;

@RestController
@EnableAutoConfiguration
@RequestMapping("/saas")
public class RestAppController {
	
	@Autowired
	private NodeService service;

	@RequestMapping(value = "/node", method = RequestMethod.POST)
	public Long createNode(@RequestBody Node node) {
		return service.saveNode(node);
	}
	
	@RequestMapping(value = "/node", method = RequestMethod.PUT)
	public Long updateNode(@RequestBody Node node) {
		return service.updateNode(node);
	}
	
	@RequestMapping(value = "/node/{parentId}", method = RequestMethod.GET, produces = "application/json")
	public List<NodeChildDTO> findNodesByParentId(@PathVariable("parentId") Long parentId) {
		return service.findByParentId(parentId);
	}
	
	@RequestMapping(value = "/node", method = RequestMethod.GET, produces = "application/json")
	public List<Node> findAllNodes() {
		return service.findEntireTree();
	}

}