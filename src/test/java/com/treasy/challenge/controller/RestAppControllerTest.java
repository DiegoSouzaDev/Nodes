package com.treasy.challenge.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.repository.NodeRepository;
import com.treasy.challenge.service.NodeService;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { RestAppController.class }, loader = SpringBootContextLoader.class)
@WebAppConfiguration
@SpringBootTest
public class RestAppControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private NodeService service;
	
	@MockBean
	private NodeRepository repos;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}
	
	@Ignore
	@Test
	public void testCreateNode() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = populateDTO(10L, "111", "desc1", "dtl1", 0L);
		final Node node = populateNode(10l, "111", "desc1", "dtl1");
		
		final String jsonResultContent = "{\"id\": 10}";
		System.out.println(jsonResultContent);
		when(repos.save(new Node())).thenReturn(node);
		
		final MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders.post("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto))).andReturn();
		// .andExpect(status().isOk()).andExpect(content().json(jsonResultContent));
		verify(service, times(1)).saveOrUpdateNode(dto);

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

	@Test
	public void testFindAllNodes() throws Exception {

		when(repos.findEntireTree()).thenReturn(getNewTree());
		mockMvc.perform(MockMvcRequestBuilders.get("/saas/node")).andExpect(status().isOk()).andExpect(content().string(equalTo("{\"id\": 10}")));
	}
	
	private NodeDTO populateDTO(final Long id, final String code, final String desc, final String detail, final Long parentId) {
		final NodeDTO dto = new NodeDTO();
		dto.setId(id);
		dto.setCode(code);
		dto.setDescription(desc);
		dto.setDetail(detail);
		dto.setParentId(parentId);
		return dto;
	}

	private Node populateNode(final Long id, final String code, final String desc, final String detail) {
		final Node node = new Node();
		node.setId(id);
		node.setCode(code);
		node.setDescription(desc);
		node.setDetail(detail);
		return node;
	}

}
