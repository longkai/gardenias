package cn.longkai.gardenias.repository.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookingInfoDao;
import cn.longkai.gardenias.util.LibraryUtil;
import cn.longkai.gardenias.util.Pagination;

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
		= "SELECT COUNT(t.id) FROM BookingInfo t where t.reader = ? AND t.cancel IS false AND t.deal IS false and t.bookDate < ?";
	
	/** 查询一本书有效地预约数量 */
	private static final String SELECT_HAS_BEEN_BOOKED_BY_BOOK
		= "FROM BookingInfo t where t.book = ? AND t.cancel IS false AND t.deal IS false";
	
	/** 查询该读者有效预定的该图书 */
	private static final String SELECT_HAS_BEEN_BOOKED_BY_READER_AND_BOOK
		= "FROM BookingInfo t where t.book = :book AND t.reader = :reader AND t.cancel IS false AND t.deal IS false";
	
	/** 查询读者信息列表 */
	private static final String QUERY_LIST
		= "FROM BookingInfo t WHERE t.reader = ? ORDER BY t.date DESC";
	
//	直接在hql中完成更新！
	/** 更新已经过期的该本预约图书 */
//	private static final String UPDATE_RUNOUT
//		= "UPDATE BookingInfo t SET t.deal = true WHERE t.deal IS false and t.cancel IS false and t.book";
//		= "FROM BookingInfo t WHERE t.book = ? AND t.deal IS false AND t.cancel IS false";
	
	@Override
	public int howManyBooksBeenBooked(Reader reader) {
		Long counter = em.createQuery(SELECT_BOOKED_COUNT_BY_READER, Long.class)
				.setParameter(1, reader)
				.setParameter(2, new Date())
				.getSingleResult();
		
		return counter.intValue();
	}

	@Override
	public boolean hasBeenBooked(Book book) {
		BookingInfo bookingInfo = null;
		try {
//			bookingInfo = super.executeQuery(SELECT_HAS_BEEN_BOOKED_BY_BOOK, book);
			bookingInfo = super.executeQuery(SELECT_HAS_BEEN_BOOKED_BY_BOOK, BookingInfo.class, book);
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
		boolean runout = LibraryUtil.runout(bookingInfo.getDate(), LIbraryConstant.MAX_BOOKING_DATE);
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

	/**
	 * 刷新一下预约信息中仍未处理的过期的信息
	 */
	@Override
	public void refresh(Book book) {
		List<BookingInfo> bookingInfos = em.createQuery(SELECT_HAS_BEEN_BOOKED_BY_BOOK, BookingInfo.class)
				.setParameter(1, book)
				.getResultList();
		for (BookingInfo bookingInfo : bookingInfos) {
			if (!this.booked(bookingInfo)) {
				bookingInfo.setDeal(true);
				super.merge(bookingInfo);
			}
		}
	}

	@Override
	public Pagination<BookingInfo> list(Reader reader, int offset, int size) {
		return super.queryForList(BookingInfo.class, QUERY_LIST, reader, offset, size);
	}
	
}
