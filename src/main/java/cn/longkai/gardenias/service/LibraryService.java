package cn.longkai.gardenias.service;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;

/**
 * 图书馆对外服务接口。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface LibraryService {

	/**
	 * 读者预定图书。
	 * @param book 欲预定的图书
	 * @param reader 想要预定的读者
	 * @return 若成功，返回一条预定图书的记录
	 */
	BookingInfo book(Book book, Reader reader);
	
	/**
	 * 读者借阅图书。
	 * @param book 欲借阅的图书
	 * @param reader 想要借阅的读者
	 * @return 若成功，返回一条借阅图书的记录
	 */
	LendInfo lend(Book book, Reader reader);
	
	/**
	 * 读者归还图书。
	 * @param book 欲归还的图书
	 * @param reader 想要还书的读者
	 * @return 若成功，返回修改后（添加了归还时间）的借阅图书记录
	 */
	LendInfo _return(Book book, Reader reader);
	
	/**
	 * 读者归还罚款。
	 * @param book 为那本书交付的欠款
	 * @param reader 交付欠款的读者
	 * @return 若成功，返回修改后（添加了罚款时间）的罚款记录
	 */
	ChargeInfo charge(Book book, Reader reader);

	/**
	 * 取消预约图书
	 * @param book
	 * @param reader
	 */
	BookingInfo cancel(Book book, Reader reader);
	
}
