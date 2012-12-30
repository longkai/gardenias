package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.Reader;

/**
 * 图书预定信息数据访问接口。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface BookingInfoDao extends GenericDao<BookingInfo>, InfoAware<BookingInfo> {

	/**
	 * 该读者<b style="color: red">在预约状态中</b>的图书的记录数。
	 * @param reader 欲查询的读者。
	 * @return 在预约状态中的图书的记录数
	 */
	int howManyBooksBeenBooked(Reader reader);
	
	/**
	 * 该书（<b style="color: red">在预约状态中</b>）是否被预定。
	 * @param book
	 * @return 该书是否存在在预约状态中
	 */
	boolean hasBeenBooked(Book book);
	
	/**
	 * 该读者是否预订过该书，并且预定在<b style="color: red;">尚处于有效期</b>之中。
	 * @param book
	 * @param reader
	 */
	boolean hasBeenBooked(Book book, Reader reader);

	/**
	 * 对该书的预约信息进行刷新。
	 * @param book
	 */
	void refresh(Book book);
	
//	抽象出一个接口，这样更简洁些了-_-
//	/**
//	 * 返回该用户预约（<b style="color: red;">在有效期的</b>）该本书。
//	 * @param book
//	 * @param reader
//	 */
//	BookingInfo find(Book book, Reader reader);
	
}
