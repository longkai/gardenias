package cn.longkai.gardenias.repository.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.util.Pagination;

/**
 * 图书的数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class BookDaoImpl extends GeneralDaoImpl<Book> implements BookDao {

	/** 查询 某一类别的图书 */
	private static final String QUERY_BOOKS_BY_CATEGORY = "FROM Book b WHERE b.category.id = ";
	
	@Override
	public Pagination<Book> list(int NO, int howMany, Category category) {
		List<Book> books = em.createQuery(QUERY_BOOKS_BY_CATEGORY + category.getId(), Book.class)
//				.setParameter(1, category)
				.getResultList();
		Pagination<Book> p = new Pagination<>(NO, howMany, books.size());
		return p.setList(books);
	}

}
