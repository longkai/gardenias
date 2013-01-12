package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.util.Pagination;

/**
 * 图书的数据访问接口。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface BookDao extends GenericDao<Book> {

	/**
	 * 获取某个类别的图书
	 * @param NO
	 * @param howMany
	 * @param category
	 */
	Pagination<Book> list(int NO, int howMany, Category category);
	
	/**
	 * 查找，通过title
	 * @param offset
	 * @param howMany
	 * @param title
	 */
	Pagination<Book> findByTitle(int offset, int howMany, String title);
	
	/**
	 * 查找，通过作者名。
	 * @param offset
	 * @param howMany
	 * @param author
	 */
	Pagination<Book> findByAuthor(int offset, int howMany, String author);
	
}
