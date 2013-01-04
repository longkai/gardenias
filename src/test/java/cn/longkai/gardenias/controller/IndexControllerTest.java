package cn.longkai.gardenias.controller;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import cn.longkai.gardenias.controller.IndexController;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@WebAppConfiguration
public class IndexControllerTest {

	@Inject
	private WebApplicationContext context;

	@Inject
	private IndexController indexController;

	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
	}

	@Test
	public void hello() throws Exception {
		this.mockMvc.perform(get("/").accept(MediaType.TEXT_HTML))
				.andExpect(status().isOk())
				.andExpect(content().contentType("text/html;charset=utf-8"));
	}

	@Test
	public void hi() {
		assertThat(this.indexController.index().getViewName(), is("hello"));
	}
	
	@Test
	public void t() {
//		this.mockMvc.perform(post("/login", null).param("account", "longkai").param("password", "123456"));
		System.out.println(indexController.login("longkai", "123456").getModel().get("reader"));
	}

}