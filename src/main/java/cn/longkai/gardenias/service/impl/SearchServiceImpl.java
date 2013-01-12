package cn.longkai.gardenias.service.impl;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.service.SearchService;
import cn.longkai.gardenias.util.Pagination;

/**
 * 查询服务接口的实现。
 * 
 * @author longkai
 * @since 2013-01-13
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SearchServiceImpl implements SearchService {

	@Inject
	private BookDao bookDao;
	
	@Override
	public Pagination<Book> searchByTile(String title, int NO) {
		return bookDao.findByTitle(NO, LIbraryConstant.MAX_RECORDS_PER_PAGE, title);
	}

	@Override
	public Pagination<Book> searchByAuthor(String author, int NO) {
		return bookDao.findByAuthor(NO, LIbraryConstant.MAX_RECORDS_PER_PAGE, author);
	}

}
