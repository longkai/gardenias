package cn.longkai.gardenias.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.util.LibraryException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/spring.xml")
@Transactional
public class LibraryServiceTest {

	private static final Logger l = LoggerFactory.getLogger(LibraryServiceTest.class);
	
	@Inject
	private LibraryService libraryService;
	@Inject
	private ReaderService readerService;
	@Inject
	private BookDao bookDao;
	
	private Reader testReader;
	private Book targetBook;
	
	private Reader readerWithCharge;
	private Book bookHasNoRemain;
	private Book book3;
	private Book bookHasCharge;
	private Reader readerHasBookedBook3;
	
	@Before
	public void setUp() {
		testReader = readerService.login("longkai", "123456");
		
		targetBook = bookDao.find(2, Book.class);
		l.debug("目标图书: {}，剩余数量：{}", targetBook.getTitle(), targetBook.getRemain());
		bookHasNoRemain = bookDao.find(1, Book.class);
		l.debug("目标图书: {}，剩余数量：{}", bookHasNoRemain.getTitle(), bookHasNoRemain.getRemain());
		readerWithCharge = readerService.login("charged_user", "123456");
		l.debug("测试读者: {}", readerWithCharge.getNick());
		readerHasBookedBook3 = readerService.login("booked_book3_user", "123456");
		l.debug("测试读者: {}", readerWithCharge.getNick());
		
		book3 = bookDao.find(3, Book.class);
		l.debug("目标图书: {}，剩余数量：{}", bookHasNoRemain.getTitle(), bookHasNoRemain.getRemain());
		
		bookHasCharge = bookDao.find(5, Book.class);
		l.debug("目标图书: {}，剩余数量：{}", bookHasNoRemain.getTitle(), bookHasNoRemain.getRemain());
	}
	
//	因为这本书有剩余，所以预约会失败！
	@Test(expected = LibraryException.class)
	public void testBookHasRemain() {
		libraryService.book(targetBook, testReader);
	}
	
	@Test
	public void testBookHasNoRemain() {
		libraryService.book(bookHasNoRemain, testReader);
	}
	
	@Test(expected = LibraryException.class)
	public void testBookButTheReaderHasCharge() {
		libraryService.book(targetBook, readerWithCharge);
	}
	
	@Test(expected = LibraryException.class)
	public void testBookOneBookTwice() {
		libraryService.book(book3, readerHasBookedBook3);
	}

	@Test
	public void testLend() {
		libraryService.lend(targetBook, testReader);
	}

	@Test
	public void test_return() {
		int tempRemain = targetBook.getRemain();
		libraryService._return(targetBook, testReader);
		assertThat(targetBook.getRemain(), is(tempRemain + 1));
	}

	@Test(expected = LibraryException.class)
	public void testChargeButNoCharge() {
		libraryService.charge(targetBook, testReader);
	}
	
	@Test
	public void testCharge() {
		ChargeInfo c = libraryService.charge(bookHasCharge, readerWithCharge);
//		assertThat(c, notNullValue());
		assertThat(c.getChargeDate(), notNullValue());
	}

}
