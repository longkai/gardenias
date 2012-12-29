package cn.longkai.gardenias.repository.impl;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import javax.inject.Inject;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.repository.BookingInfoDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class BookingInfoDaoImplTest {

	@Inject
	private BookingInfoDao bookingInfoDao;
	
	@Test
	public void testHowManyBooksBeenBooked() {
		int count = bookingInfoDao.howManyBooksBeenBooked(null);
		assertThat(count, is(0));
	}

}
