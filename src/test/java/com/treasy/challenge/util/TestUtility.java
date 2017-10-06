package com.treasy.challenge.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.model.Node;

public class TestUtility {

	public Node getNewNode(final Long id, final String code, final String detail, final String description) {
		final Node node = new Node();
		node.setId(id);
		node.setCode(code);
		node.setDescription(description);
		node.setDetail(detail);
		return node;
	}
	
	public List<Node> getNewTree() {
		final Node node1 = getNewNode(1l, "code1", "detail1", "description1");
		final Node node2 = getNewNode(2l, "code2", "detail2", "description2");
		final Node node3 = getNewNode(3l, "code3", "detail3", "description3");
		final Node node4 = getNewNode(4l, "code4", "detail4", "description4");
		final Node node5 = getNewNode(5l, "code5", "detail5", "description5");
		final Node node6 = getNewNode(6l, "code6", "detail6", "description6");
		final Node node7 = getNewNode(7l, "code7", "detail7", "description7");
		final Node node8 = getNewNode(8l, "code8", "detail8", "description8");
		
		node3.setParent(node1);
		node4.setParent(node1);
		node5.setParent(node2);
		node6.setParent(node2);
		node7.setParent(node1);
		node8.setParent(node2);
		
		node2.setChildren(Arrays.asList(node5, node6, node8));
		node1.setChildren(Arrays.asList(node3, node4, node7));
		final List<Node> nodeList = new ArrayList<Node>();
		nodeList.add(node1);
		nodeList.add(node2);
		return nodeList;
	}
	
	public NodeDTO getNewNodeDTO(final Long id, final String code, final String description, final String detail, final Long parentId,
			final boolean hasChildren) {
		final NodeDTO nodeDTO = new NodeDTO();
		nodeDTO.setId(id);
		nodeDTO.setCode(code);
		nodeDTO.setDescription(description);
		nodeDTO.setDetail(detail);
		if (parentId != null && parentId != 0L) {
			nodeDTO.setParentId(parentId);
		}
		nodeDTO.setHasChildren(hasChildren);
		return nodeDTO;
	}
	
	public List<NodeDTO> getNodeDTOList() {
		final NodeDTO dto1 = getNewNodeDTO(10L, "code", "description", "detail", 1L, true);
		final NodeDTO dto2 = getNewNodeDTO(11L, "code", "description", "detail", 1L, false);
		
		return Arrays.asList(dto1, dto2);
	}

}
