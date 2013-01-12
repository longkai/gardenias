package cn.longkai.gardenias.service;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.util.Pagination;

/**
 * 搜索的服务接口，这里仅仅是简单的罗列的两个方法充当门面-_-
 * 
 * @author longkai
 * @since 2013-01-13
 * @version 1.0
 */
public interface SearchService {

	/**
	 * 通过图书的名字来查找图书。
	 * 
	 * @param bookName
	 * @param NO 第几页
	 */
	Pagination<Book> searchByTile(String title, int NO);

	/**
	 * 通过作者来查询。
	 * 
	 * @param author
	 * @param NO
	 */
	Pagination<Book> searchByAuthor(String author, int NO);

}
