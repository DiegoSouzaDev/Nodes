package com.treasy.challenge.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.repository.NodeRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NodeServiceTest {

	@MockBean
	NodeRepository repos;
	
	@Autowired
	private NodeService service;
	
	@Test
	public void testFindEntireTree() throws Exception {
		final List<Node> entireTree = getNewTree();
		when(repos.findEntireTree()).thenReturn(entireTree);

		final List<Node> returnList = service.findEntireTree();

		int nodeCount = returnList.size();
		for (final Node node : returnList) {
			nodeCount += node.getChildren().size();
		}
		Assert.assertEquals(nodeCount, 8);
	}

	@Test
	public void testFindByParentId() throws Exception {
		final List<Node> entireTree = getNewTree();
		when(repos.findByParentId(1L)).thenReturn(entireTree.get(0).getChildren());
		when(repos.findByParentId(2L)).thenReturn(entireTree.get(1).getChildren());

		final List<NodeDTO> dtoList1 = service.findByParentId(1L);
		final List<NodeDTO> dtoList2 = service.findByParentId(2L);
		Assert.assertEquals(dtoList1.size(), 3);
		Assert.assertEquals(dtoList2.size(), 3);
		
	}

	@Test
	public void testConvertToDTOList() {
		final Node parent = getNewNode(10l, "code19", "detail19", "description19");
		final Node node = getNewNode(20l, "code20", "detail", "description");
		final Node child1 = getNewNode(21l, "code21", "detail", "description");

		node.setParent(parent);
		node.setChildren(Arrays.asList(child1));

		final NodeDTO nodeDTO = service.convertToChildDTOList(Arrays.asList(node)).get(0);
		
		Assert.assertEquals(node.getId(), nodeDTO.getId());
		Assert.assertEquals(node.getCode(), nodeDTO.getCode());
		Assert.assertEquals(node.getDescription(), nodeDTO.getDescription());
		Assert.assertEquals(node.getDetail(), nodeDTO.getDetail());
		
		Assert.assertEquals(nodeDTO.getHasChildren(), Boolean.TRUE);
	}
	
	@Test
	public void testSaveOrUpdateNode() throws Exception {
		final NodeDTO nodeDTO = getNewNodeDTO(10l, "code", "detail", "description", 0L);
		final Node node = service.convertToNode(nodeDTO);
		when(repos.save(service.convertToNode(nodeDTO))).thenReturn(node);

		final String expectedJson = "{\"id\": 10}";
		final String returnedJson = service.saveOrUpdateNode(nodeDTO);
		
		Assert.assertEquals(returnedJson, expectedJson);
	}
	
	@Test
	public void testGeneratePersistenceJsonReturn() throws Exception {
		final Node node = getNewNode(10l, "code", "detail", "description");
		
		final String expectedReturn = "{\"id\": 10}";
		final String jsonReturn = service.generatePersistenceJsonReturn(node);
		Assert.assertEquals(expectedReturn, jsonReturn);
	}
	
	@Test
	public void testConvertToNodeWithNoParent() throws Exception {
		final NodeDTO nodeDTO = getNewNodeDTO(1L, "code1", "detail1", "description1", 0L);
		final Node node = service.convertToNode(nodeDTO);
		Assert.assertEquals(nodeDTO.getId(), node.getId());
		Assert.assertEquals(nodeDTO.getCode(), node.getCode());
		Assert.assertEquals(nodeDTO.getDetail(), node.getDetail());
		Assert.assertEquals(nodeDTO.getDescription(), node.getDescription());
		
		Assert.assertEquals(node.getParent(), null);
	}
	
	@Test
	public void testConvertToNodeWithValidParent() throws Exception {
		final NodeDTO nodeDTO = getNewNodeDTO(2L, "code2", "detail2", "description2", 1L);
		final Node node = service.convertToNode(nodeDTO);
		Assert.assertEquals(nodeDTO.getId(), node.getId());
		Assert.assertEquals(nodeDTO.getCode(), node.getCode());
		Assert.assertEquals(nodeDTO.getDetail(), node.getDetail());
		Assert.assertEquals(nodeDTO.getDescription(), node.getDescription());
		Assert.assertEquals(nodeDTO.getParentId(), node.getParent().getId());
	}
	
	public NodeDTO getNewNodeDTO(final Long id, final String code, final String detail, final String description, final Long parentId) {
		final NodeDTO nodeDTO = new NodeDTO();
		nodeDTO.setId(id);
		nodeDTO.setCode(code);
		nodeDTO.setDescription(description);
		nodeDTO.setDetail(detail);
		nodeDTO.setParentId(parentId);
		return nodeDTO;
	}
	
	private Node getNewNode(final Long id, final String code, final String detail, final String description) {
		final Node node = new Node();
		node.setId(id);
		node.setCode(code);
		node.setDescription(description);
		node.setDetail(detail);
		return node;
	}
	
	private List<Node> getNewTree() {
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
	
}
