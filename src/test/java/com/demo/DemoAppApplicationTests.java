package com.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.demo.model.Book;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class) //specifies to use ordering while running test methods
@TestInstance(Lifecycle.PER_CLASS)
class DemoAppApplicationTests {

	@Autowired
	private WebApplicationContext context;

	ObjectMapper wrapper = new ObjectMapper();

	private MockMvc mockMvc;

	@BeforeAll
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

	}

	@Test
	@Order(1) // specifies the order in which  methods annotated with @test should be called
	public void testAddBook() throws Exception {
		Book book = new Book();
		book.setBookName("Data Structure2");
		book.setAuthor("Padma Reddy");
		book.setPublisherName("Penguin Publishers");

		// convert book object to string JSON format
		String jsonRequest = wrapper.writeValueAsString(book);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/addBook").content(jsonRequest)
				.contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(status().isOk()).andReturn();

		int status = result.getResponse().getStatus();
		assertEquals(200, status);
		// String content = result.getResponse().getContentAsString();
		// Book newBook = wrapper.readValue(content, Book.class);
		// assertEquals("Data Structure2", newBook.getBookName());

	}

	@Test
	@Order(2)
	public void testGetBooks() throws Exception {
		Book book = new Book();
		book.setBookName("Data Structure2");
		book.setAuthor("Padma Reddy");
		book.setPublisherName("Penguin Publishers");

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/getBooks").contentType("application/json"))
				.andExpect(status().isOk()).andReturn();

		String content = result.getResponse().getContentAsString();
		Book[] bookList = mapFromJson(content, Book[].class);
		assertTrue(bookList.length > 0);
	}

	@Test
	@Order(3)
	public void updateBook() throws Exception {
		String uri = "/updateBook/Data Structure2";
		Book book = new Book();
		book.setBookName("Data Structure2");
		book.setAuthor("Jason");
		book.setPublisherName("Penguin Publishers");
		String inputJson = mapToJson(book);
		MvcResult mvcResult = mockMvc.perform(
				MockMvcRequestBuilders.put(uri).contentType(MediaType.APPLICATION_JSON_VALUE).content(inputJson))
				.andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	@Test
	@Order(4)
	@DisplayName("testing delete book method")
	public void deleteBook() throws Exception {
		String uri = "/deleteBook/Data Structure2";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)).andReturn();
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
	}

	protected String mapToJson(Object obj) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.writeValueAsString(obj);
	}

	protected <T> T mapFromJson(String json, Class<T> clazz)
			throws JsonParseException, JsonMappingException, IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		return objectMapper.readValue(json, clazz);
	}

}
