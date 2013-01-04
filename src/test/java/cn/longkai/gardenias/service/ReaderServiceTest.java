package cn.longkai.gardenias.service;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.entity.Reader;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class ReaderServiceTest {

	@Inject
	private ReaderService readerService;
	
	private Reader testReader;
	
	@Before
	public void setUp() {
		testReader = new Reader();
		testReader.setAccount("longkai2");
		testReader.setAddress("gxu");
		testReader.setAge(21);
		testReader.setEmail("im.longkai@gmail.com");
		testReader.setIdCard("45030219911013xxxx");
		testReader.setName("龙凯");
		testReader.setNick("爱因斯坦的狗");
		testReader.setPassword("123456");
		testReader.setSex(true);
	}
	
	@Test
	public void testRegister() {
		readerService.register(testReader);
		assertThat(readerService.login(testReader.getAccount(), testReader.getPassword()).getIdCard(), is(testReader.getIdCard()));
	}

	@Test
	public void testLogin() {
		assertThat(readerService.login("longkai", "123456").getName(), is("龙凯")); // 已经插入，直接测试！
//		assertThat(readerService.login(testReader.getAccount(), testReader.getPassword()).getIdCard(), is(testReader.getIdCard()));
	}

}
