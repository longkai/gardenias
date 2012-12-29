package cn.longkai.gardenias.repository;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.entity.Reader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class ChargeInfoDaoTest {

	@Inject
	private ChargeInfoDao chargeInfoDao;
	
	private Reader reader;
	
	@Test
	public void testHowManyChargeBooks() {
		int count = chargeInfoDao.howManyChargeBooks(reader);
		assertThat(count, is(0));
	}

}
