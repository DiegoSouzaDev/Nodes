package com.treasy.challenge.controller;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.treasy.challenge.dto.NodeDTO;
import com.treasy.challenge.exception.InvalidParentException;
import com.treasy.challenge.model.Node;
import com.treasy.challenge.service.NodeService;
import com.treasy.challenge.util.TestUtility;

@RunWith(SpringRunner.class)
@WebAppConfiguration
@SpringBootTest
public class RestAppControllerTest extends TestUtility {

	private MockMvc mockMvc;

	@Autowired()
	private WebApplicationContext wac;

	@MockBean
	private NodeService service;

	@Before
	public void setup() throws Exception {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

	}

	@Test
	public void testFindNodesByParentId() throws Exception {
		final List<NodeDTO> nodeDTOList = getNodeDTOList();
		final String jsonExpected = "[{\"id\":10,\"code\":\"code\",\"description\":\"description\",\"detail\":\"detail\",\"parentId\":1,\"hasChildren\":true},{\"id\":11,\"code\":\"code\",\"description\":\"description\",\"detail\":\"detail\",\"parentId\":1,\"hasChildren\":false}]";

		when(service.findByParentId(1L)).thenReturn(nodeDTOList);

		mockMvc.perform(get("/saas/node/1")).andExpect(status().isOk()).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
				.andExpect(content().string(equalTo(jsonExpected)));
	}

	@Test
	public void testFindAllNodes() throws Exception {
		final List<Node> nodeList = getNewTree();
		final String jsonExpected = "[{\"id\":1,\"code\":\"code1\",\"description\":\"description1\",\"detail\":\"detail1\",\"parent\":null,\"children\":[{\"id\":3,\"code\":\"code3\",\"description\":\"description3\",\"detail\":\"detail3\",\"parent\":1,\"children\":null},{\"id\":4,\"code\":\"code4\",\"description\":\"description4\",\"detail\":\"detail4\",\"parent\":1,\"children\":null},{\"id\":7,\"code\":\"code7\",\"description\":\"description7\",\"detail\":\"detail7\",\"parent\":1,\"children\":null}]},{\"id\":2,\"code\":\"code2\",\"description\":\"description2\",\"detail\":\"detail2\",\"parent\":null,\"children\":[{\"id\":5,\"code\":\"code5\",\"description\":\"description5\",\"detail\":\"detail5\",\"parent\":2,\"children\":null},{\"id\":6,\"code\":\"code6\",\"description\":\"description6\",\"detail\":\"detail6\",\"parent\":2,\"children\":null},{\"id\":8,\"code\":\"code8\",\"description\":\"description8\",\"detail\":\"detail8\",\"parent\":2,\"children\":null}]}]";

		when(service.findEntireTree()).thenReturn(nodeList);

		mockMvc.perform(MockMvcRequestBuilders.get("/saas/node")).andExpect(status().isOk()).andExpect(content().string(equalTo(jsonExpected)));
	}

	@Test
	public void testCreateNode() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = getNewNodeDTO(10L, "code10", "desc10", "dtl10", 1L, false);
		final Node node = getNewNode(10L, "code10", "detail10", "description10");
		final StringBuilder sb = new StringBuilder();

		final String jsonExpected = "{\"id\": 10}";
		final String jsonReturn = sb.append("{\"id\": ").append(node.getId().toString()).append("}").toString();

		when(service.saveNode(Mockito.any())).thenReturn(jsonReturn);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk()).andExpect(content().string(equalTo(jsonExpected)));
	}

	@Test
	public void testCreateNodeInvalidParent() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = getNewNodeDTO(10L, "code10", "desc10", "dtl10", 10L, false);
		final String expectedReturn = "Could not use the specified parentID";

		when(service.saveNode(Mockito.any())).thenThrow(InvalidParentException.class);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest()).andExpect(content().string(equalTo(expectedReturn)));
	}

	@Test
	public void testDataIntegrityOnCreateNode() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = getNewNodeDTO(10L, "code10", "desc10", "dtl10", 1L, false);
		final String expectedMessage = "Não foi possivel encontrar o nó informado como 'parentId'";

		when(service.saveNode(Mockito.any())).thenThrow(DataIntegrityViolationException.class);

		this.mockMvc
				.perform(MockMvcRequestBuilders.post("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest()).andExpect(content().string(equalTo(expectedMessage)));
	}

	@Test
	public void testUpdateNode() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = getNewNodeDTO(11L, "code11", "desc11", "dtl11", 0L, false);
		final Node node = getNewNode(11L, "code11", "detail11", "description11");
		final String jsonExpected = "{\"id\": 11}";
		final StringBuilder sb = new StringBuilder();
		final String jsonReturn = sb.append("{\"id\": ").append(node.getId().toString()).append("}").toString();

		when(service.updateNode(Mockito.any())).thenReturn(jsonReturn);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isOk()).andExpect(content().string(equalTo(jsonExpected)));
	}

	@Test
	public void testDeleteNode() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.delete("/saas/node/1")).andExpect(status().isOk()).andExpect(content().string(equalTo("")));
	}

	@Test
	public void testEmptyResultWhenDeleteNode() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = getNewNodeDTO(11L, "code11", "desc11", "dtl11", 0L, false);
		final String expectedReturn = "Nó inexistente. Nenhum nó será removido";

		when(service.updateNode(Mockito.any())).thenThrow(EmptyResultDataAccessException.class);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest()).andExpect(content().string(equalTo(expectedReturn)));
	}

	@Test
	public void testNodeEntityNotFoundToUpdate() throws Exception {
		final ObjectMapper mapper = new ObjectMapper();
		final NodeDTO dto = getNewNodeDTO(11L, "code11", "desc11", "dtl11", 0L, false);
		final String expectedReturn = "Não foi possivel encontrar o nó a ser atualizado ou o nó informado como 'parentId'";

		when(service.updateNode(Mockito.any())).thenThrow(EntityNotFoundException.class);

		this.mockMvc
				.perform(MockMvcRequestBuilders.put("/saas/node").accept(MediaType.APPLICATION_JSON_VALUE)
						.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(dto)))
				.andExpect(status().isBadRequest()).andExpect(content().string(equalTo(expectedReturn)));
	}

}
