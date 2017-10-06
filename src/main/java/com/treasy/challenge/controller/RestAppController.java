package com.treasy.challenge.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.service.NodeService;

@RestController
@RequestMapping("/saas")
public class RestAppController {
	
	@Autowired
	private NodeService service;

	@RequestMapping(value = "/node", method = RequestMethod.POST)
	public String createNode(@RequestBody final NodeDTO nodeDTO) {
		return service.saveOrUpdateNode(nodeDTO);
	}
	
	@RequestMapping(value = "/node", produces = "application/json", method = RequestMethod.PUT)
	public String updateNode(@RequestBody final NodeDTO nodeDTO) {
		return service.saveOrUpdateNode(nodeDTO);
	}
	
	@RequestMapping(value = "/node/{parentId}", method = RequestMethod.GET, produces = "application/json")
	public List<NodeDTO> findNodesByParentId(@PathVariable("parentId") final Long parentId) {
		return service.findByParentId(parentId);
	}
	
	@RequestMapping(value = "/node", method = RequestMethod.GET, produces = "application/json")
	public List<Node> findAllNodes() {
		return service.findEntireTree();
	}
	
	@RequestMapping(value = "/node/{id}", method = RequestMethod.DELETE)
	public void removeNode(@PathVariable("id") final Long id) {
		service.deleteNode(id);
	}

}