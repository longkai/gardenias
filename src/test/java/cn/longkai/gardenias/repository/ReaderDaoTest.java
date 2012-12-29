package cn.longkai.gardenias.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Date;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.util.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class ReaderDaoTest {

	@Inject
	private ReaderDao readerDao;
	
	private Reader reader;
	
	@Before
	public void setUp() {
		reader = new Reader();
		reader.setAccount("longkai");
		reader.setAddress("广西桂林");
		reader.setAge(21);
		reader.setEmail("im.longkai@gmail.com");
		reader.setIdCard("45030219911013xxxx");
		reader.setName("龙凯");
		reader.setNick("爱因斯坦的狗");
		reader.setPassword("123456");
		reader.setSex(false);
		
//		should be set in service!
		reader.setRegisterTime(new Date());
	}
	
	@Test
	public void testPersist() {
		int size = readerDao.size(Reader.class);
		readerDao.persist(reader);
		assertThat(readerDao.size(Reader.class), is(size + 1));
	}
	
	@Test(expected = RuntimeException.class)
	public void testPersistWithNullReader() {
		reader = null;
		readerDao.persist(reader);
	}
	
	@Test
	public void testSize() {
		assertThat(readerDao.size(Reader.class), is(0));
	}
	
	@Test
	public void testExists() {
		readerDao.persist(reader);
		assertThat(readerDao.exists(reader.getAccount()), is(true));
	}
	
	@Test
	public void testFindByAccountAndPassword() {
		readerDao.persist(reader);
		Reader r = readerDao.find(reader.getAccount(), reader.getPassword());
		assertThat(r.equals(reader), is(true));
	}
	
	@Test
	@Ignore("测试一成成功后即可")
	public void testPagination() {
		for (int i = 10; i < 30; i++) {
//			must set id or it'll not be persisted!
			reader.setId(i);
//			should use merge instead of persist!
			readerDao.merge(reader);
		}
		assertThat(readerDao.size(Reader.class), is(20));
		Pagination<Reader> p = readerDao.list(1, 15, Reader.class);
		assertThat(p.getTotalRecords(), is(20));
		assertThat(p.getTotal(), is(2));
		assertThat(p.getPage(), is(1));
		
		p = readerDao.list(2, 15, Reader.class);
		assertThat(p.getList().size(), is(5));
	}
	
}
