package cn.longkai.gardenias.repository.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookingInfoDao;
import cn.longkai.gardenias.util.LibraryUtil;

/**
 * 图书预定数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class BookingInfoDaoImpl extends GeneralDaoImpl<BookingInfo> implements BookingInfoDao {
	
	/** 查询有效的图书预约数量 */
	private static final String SELECT_BOOKED_COUNT_BY_READER 
		= "SELECT COUNT(bi.id) FROM BookingInfo bi where bi.reader = ? AND bi.cancel IS false AND bi.deal IS false and bi.bookDate < :now";
	
	/** 查询一本书有效地预约数量 */
	private static final String SELECT_HAS_BEEN_BOOKED_BY_BOOK
		= "FROM BookingInfo bi where bi.book = ? AND bi.cancel IS false AND bi.deal IS false";
	
	/** 查询该读者有效预定的该图书 */
	private static final String SELECT_HAS_BEEN_BOOKED_BY_READER_AND_BOOK
		= "FROM BookingInfo bi where bi.book = :book AND bi.reader = :reader AND bi.cancel AND bi.deal IS false";
	
//	直接在hql中完成更新！
	/** 更新已经过期的该本预约图书 */
	private static final String UPDATE_RUNOUT
		= "UPDATE BookingInfo bi SET bi.deal = true WHERE bi.deal IS false and bi.cancel IS false and bi.book";
	
	@Override
	public int howManyBooksBeenBooked(Reader reader) {
		Long counter = em.createQuery(SELECT_BOOKED_COUNT_BY_READER, Long.class)
				.setParameter(1, reader)
				.getSingleResult();
		
		return counter.intValue();
	}

	@Override
	public boolean hasBeenBooked(Book book) {
		BookingInfo bookingInfo = null;
		try {
			bookingInfo = this.executeQuery(SELECT_HAS_BEEN_BOOKED_BY_BOOK);
		} catch (Exception e) {
			l.error("图书:?, 查询是否被预定时异常".replace("?", book.getTitle()), e);
			bookingInfo = null;
		}
		
		return this.booked(bookingInfo);
	}

	@Override
	public boolean hasBeenBooked(Book book, Reader reader) {
		BookingInfo bookingInfo = null;
		try {
			bookingInfo = this.executeQuery(SELECT_HAS_BEEN_BOOKED_BY_READER_AND_BOOK);
		} catch (Exception e) {
			l.error("图书:?, 查询是否被预定时异常".replace("?", book.getTitle()), e);
			bookingInfo = null;
		}
		return this.booked(bookingInfo);
	}
	
	/**
	 * 返回一个图书借阅信息实例
	 * @param hql
	 * @return
	 */
	private BookingInfo executeQuery(String hql) {
		BookingInfo bookingInfo = em.createQuery(hql, BookingInfo.class)
				.setFirstResult(0).setMaxResults(1) // 这里只需找到一条记录即可结束
				.getSingleResult();
		return bookingInfo;
	}
	
	/**
	 * 这条记录是否有效？
	 * @param bookingInfo
	 */
	private boolean booked(BookingInfo bookingInfo) {
//		如果没找着或者查找时出了异常
		if (bookingInfo == null) {
			return false;
		}
//		如果找到了，就判断一下是否预定过期了，缺省过期时间为30天，并且把deal设置为true，因为过期来没有即使来借阅，也算是图书馆处理了他的预约要求-_-
		boolean runout = LibraryUtil.runout(bookingInfo.getBookDate(), LIbraryConstant.MAX_BOOKING_DATE);
		if (runout) {
			bookingInfo.setDeal(true);
			super.merge(bookingInfo);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public BookingInfo find(Book book, Reader reader) {
		Map<String, Object> map = LibraryUtil.getNameAndValueMap(book, reader);
		BookingInfo bookingInfo = null;
		try {
			bookingInfo = super.executeQuery(SELECT_HAS_BEEN_BOOKED_BY_READER_AND_BOOK, BookingInfo.class, map);
		} catch (Exception e) {
			l.error("图书:?, 查询是否被预定时异常".replace("?", book.getTitle()), e);
			bookingInfo = null;
		}
		return bookingInfo;
	}

	@Override
	public void refresh(Book book) {
		// TODO Auto-generated method stub
		
	}
	
}
