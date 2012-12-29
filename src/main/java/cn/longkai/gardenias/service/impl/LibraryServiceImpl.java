package cn.longkai.gardenias.service.impl;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.repository.BookingInfoDao;
import cn.longkai.gardenias.repository.ChargeInfoDao;
import cn.longkai.gardenias.repository.LendInfoDao;
import cn.longkai.gardenias.service.LibraryService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.LibraryMessages;
import cn.longkai.gardenias.util.LibraryUtil;

/**
 * 图书管对外服务接口实现。
 *
 * @author longkai
 * @since 2012-12-29
 */
@Service
public class LibraryServiceImpl implements LibraryService {

	private static final Logger l = LoggerFactory.getLogger(LibraryServiceImpl.class);
	
	@Inject
	private BookDao 		bookDao;
	
	@Inject
	private LendInfoDao 	lendInfoDao;
	
	@Inject
	private ChargeInfoDao 	chargeInfoDao;
	
	@Inject
	private BookingInfoDao 	bookingInfoDao;
	
	@Override
	public BookingInfo book(Book book, Reader reader) {
		BookingInfo bookingInfo = null;
		if (bookable(book, reader)) {
			bookingInfo = BookingInfo.getInstance(book, reader);
			bookingInfoDao.persist(bookingInfo);
			l.info("读者:{} 成功 预约 图书:{}", reader.getAccount(), book.getTitle());
		}
		return bookingInfo;
	}

	@Override
	public LendInfo lend(Book book, Reader reader) {
		LendInfo lendInfo = null;
		if (this.lendable(book, reader)) {
			lendInfo = LendInfo.getInstance(book, reader);
			lendInfoDao.persist(lendInfo);
			l.info("读者:{} 成功 借阅 图书:{}", reader.getAccount(), book.getTitle());
		}
		return lendInfo;
	}

	@Override
	public LendInfo _return(Book book, Reader reader) {
		// TODO 在还书的时候处理该书的预约问题！
		return null;
	}

	@Override
	public ChargeInfo charge(Book book, Reader reader) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 该读者是否可以预约该图书，若否，则抛出相应的<b style="color: red;">运行时异常</b>。
	 * @param book
	 * @param reader
	 */
	private boolean bookable(Book book, Reader reader) {
		LibraryUtil.checkNull(book, reader);	
		
		if (this.hasRemaining(book)) {
			l.info("读者：{} -> {}", reader.getAccount(), book.getTitle(), LibraryMessages.BOOKING_FAIL_FOR_HAS_BOOKS_REMAIN);
			throw new LibraryException(LibraryMessages.BOOKING_FAIL_FOR_HAS_BOOKS_REMAIN);
		}
		
		if (this.hasCharged(reader)) {
			l.info("读者：{} -> {}", reader.getAccount(), book.getTitle(), LibraryMessages.FAIL_FOR_HAS_CHARGES);
			throw new LibraryException(LibraryMessages.FAIL_FOR_HAS_CHARGES);
		}
		
		if (this.hasMaxBookingInfo(reader)) {
			l.info("读者：{} -> {}", reader.getAccount(), LibraryMessages.BOOKING_FAIL_FOR_HAS_BOOKS_REMAIN);
			throw new LibraryException(LibraryMessages.BOOKING_FAIL_FOR_MAX_BOOKING_NUMBER);
		}
		
		return true;
	}
	
	/**
	 * 该读者是否可以借阅该图书，若否，则抛出相应的<b style="color: red;">运行时异常</b>。
	 * @param book
	 * @param reader
	 * @return
	 */
	private boolean lendable(Book book, Reader reader) {
		LibraryUtil.checkNull(book, reader);
		
		if (this.hasCharged(reader)) {
			l.info("读者：{} -> {}", reader.getAccount(), LibraryMessages.FAIL_FOR_HAS_CHARGES);
			throw new LibraryException(LibraryMessages.FAIL_FOR_HAS_CHARGES);
		}
		
		if (!this.hasRemaining(book)) {
			l.info("读者：{} -> {}", reader.getAccount(), LibraryMessages.LEND_FAIL_FOR_NONE_BOOKS);
			throw new LibraryException(LibraryMessages.LEND_FAIL_FOR_NONE_BOOKS);
		}
		
		if (this.hasMaxLendNumber(reader)) {
			l.info("读者：{} -> {}", reader.getAccount(), LibraryMessages.LEND_FAIL_FOR_MAX_BOOK_BORROWED);
			throw new LibraryException(LibraryMessages.LEND_FAIL_FOR_MAX_BOOK_BORROWED);
		}
		
//		如果已经预定了就尽早返回，采取的原则是在有效的预约期限内，先到先得-_-
		if (bookingInfoDao.hasBeenBooked(book, reader)) {
			return true;
		}
		
//		如果已经被预定，但不是自己哦0。0
		if (bookingInfoDao.hasBeenBooked(book)) {
			l.info("读者：{} -> {}", reader.getAccount(), LibraryMessages.LEND_FAIL_FOR_HAS_BEEN_BOOKED);
			throw new LibraryException(LibraryMessages.LEND_FAIL_FOR_HAS_BEEN_BOOKED);
		}
		
		return true;
	}
	
	
	/**
	 * 图书是否还有剩余。
	 * @param book
	 */
	private boolean hasRemaining(Book book) {
		return book.getRemain() > 0 ? true : false;
	}
	
	/**
	 * 读者是否有欠款。
	 * @param reader
	 */
	private boolean hasCharged(Reader reader) {
		return chargeInfoDao.howManyChargeBooks(reader) > 0 ? true : false;
	}
	
	/**
	 * 读者是否已经达到最大预约值。
	 * @param reader
	 */
	private boolean hasMaxBookingInfo(Reader reader) {
//		+1 表示假如预约上这本书后会不会超出极限
		return bookingInfoDao.howManyBooksBeenBooked(reader) + 1 > LIbraryConstant.MAX_BOOKING_NUMBER ? true : false;
	}
	
	/**
	 * 该读者是否达到了最大借书值。
	 * @param reader
	 */
	private boolean hasMaxLendNumber(Reader reader) {
//		+1 表示假如借阅上这本书后会不会超出极限
		return lendInfoDao.howManyLendedBooks(reader) + 1 > LIbraryConstant.MAX_LEND_BOOK_NUMBER ? true : false;
	}
	
	/**
	 * 该书是否已经被预约了。
	 * @param book
	 */
//	private boolean hasBeenBooked(Book book) {
//		return bookingInfoDao.hasBeenBooked(book);
//	}
	
}
