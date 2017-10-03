package com.treasy.challenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treasy.challenge.dto.NodeChildDTO;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.repository.NodeRepository;

@Service
public class NodeService {

	@Autowired
	private NodeRepository repos;
	
	public List<Node> findEntireTree() {
		return repos.findEntireTree();
	}

	public List<NodeChildDTO> findByParentId(final Long parentId) {
		return convertToChildDTOList(repos.findByParentId(parentId));
	}

	public Long saveNode(final Node node) {
		return repos.save(node).getId();
	}

	public Long updateNode(final Node node) {
		return repos.save(node).getId();
	}
	
	private List<NodeChildDTO> convertToChildDTOList(final List<Node> returnedNodeList) {

		final List<NodeChildDTO> nodeChildDTOList = new ArrayList<>();
		for (final Node node : returnedNodeList) {
			final NodeChildDTO nodeChildDTO = new NodeChildDTO();
			nodeChildDTO.setId(node.getId());
			nodeChildDTO.setCode(node.getCode());
			nodeChildDTO.setDescription(node.getDescription());
			nodeChildDTO.setDetail(node.getDetail());
			nodeChildDTO.setHasChildren(!node.getChildren().isEmpty());
			nodeChildDTO.setParentId(node.getParent().getId());

			nodeChildDTOList.add(nodeChildDTO);
		}
		return nodeChildDTOList;
	}

}
