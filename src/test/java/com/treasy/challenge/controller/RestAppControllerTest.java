package com.treasy.challenge.controller;

//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.service.NodeService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RestAppController.class }, loader = SpringBootContextLoader.class)
@WebAppConfiguration
public class RestAppControllerTest {
	
	private MockMvc mockMvc;
	
	@Autowired
	private WebApplicationContext wac;

	@MockBean
	private NodeService service;
	
	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}
	
	@Test
	public void testCreateNode() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = populateDTO();

		// final MvcResult result =
		this.mockMvc.perform(MockMvcRequestBuilders.post("/saas/node")
				.accept(MediaType.APPLICATION_JSON_VALUE).header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk());

	}

	@Ignore
	@Test
	public void testFindAllNodes() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/saas/node")).andExpect(status().isOk());
	}
	
	private NodeDTO populateDTO() {
		final NodeDTO dto = new NodeDTO();
		// dto.setId(1l);
		dto.setCode("111");
		dto.setDescription("desc1");
		dto.setDetail("dtl1");
		return dto;
	}
	
}
