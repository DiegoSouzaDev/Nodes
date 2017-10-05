package com.treasy.challenge.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.repository.NodeRepository;

@Service
public class NodeService {

	@Autowired
	private NodeRepository repos;

	public List<Node> findEntireTree() {
		return repos.findEntireTree();
	}

	public List<NodeDTO> findByParentId(final Long parentId) {
		return convertToChildDTOList(repos.findByParentId(parentId));
	}

	public String saveOrUpdateNode(final NodeDTO nodeDTO) {
		final Node node = repos.save(convertToNode(nodeDTO));
		return generatePersistenceJsonReturn(node);
	}

	// public String updateNode(final NodeDTO nodeDTO) {
	// final Node node = repos.save(convertToNode(nodeDTO));
	// return generatePersistenceJsonReturn(node);
	// }
	//
	public void deleteNode(final Long id) {
		repos.delete(id);
	}

	public String generatePersistenceJsonReturn(final Node node) {
		final StringBuilder sb = new StringBuilder();
		sb.append("{\"id\": ").append(node.getId()).append("}");
		
		return sb.toString();
	}
	
	private List<NodeDTO> convertToChildDTOList(final List<Node> returnedNodeList) {
		final List<NodeDTO> nodeChildDTOList = new ArrayList<>();
		
		for (final Node node : returnedNodeList) {
			final NodeDTO nodeChildDTO = new NodeDTO();
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

	public Node convertToNode(final NodeDTO nodeDTO) {
		final Node node = new Node();

		node.setCode(nodeDTO.getCode());
		node.setDescription(nodeDTO.getDescription());
		node.setDetail(nodeDTO.getDetail());

		if (nodeDTO.getId() != null) {
			node.setId(nodeDTO.getId());
		}

		if (nodeDTO.getParentId() != null && nodeDTO.getParentId() != 0) {
			final Node parent = new Node();
			parent.setId(nodeDTO.getParentId());
			node.setParent(parent);
		}
		
		return node;
	}
}
