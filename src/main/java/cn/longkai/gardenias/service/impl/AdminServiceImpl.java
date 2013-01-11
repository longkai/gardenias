package cn.longkai.gardenias.service.impl;

import java.util.Date;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Admin;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.AdminDao;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.repository.CategoryDao;
import cn.longkai.gardenias.repository.ReaderDao;
import cn.longkai.gardenias.service.AdminService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.LibraryMessages;
import cn.longkai.gardenias.util.Pagination;

/**
 * 管理员对外服务接口的实现
 * 
 * @author longkai
 * @since 2013-01-11
 * @version 1.0
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class AdminServiceImpl implements AdminService {

	@Inject
	private AdminDao adminDao;
	
	@Inject
	private BookDao bookDao;
	
	@Inject
	private ReaderDao readerDao;
	
	@Inject
	private CategoryDao categoryDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Admin login(Admin admin) {
		Admin a = adminDao.find(admin.getUsername(), admin.getPassword());
		a.setLastLoginTime(new Date());
		adminDao.merge(a);
		return a;
	}
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Book add(Book book) {
		book.setIncludedDate(new Date());
		bookDao.persist(book);
		return book;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Category add(Category category) {
		category.setIncludeDate(new Date());
		categoryDao.persist(category);
		return category;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Book update(Book book) {
		bookDao.merge(book);
		return book;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Category update(Category category) {
		categoryDao.merge(category);
		return category;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Reader update(Reader reader) {
		readerDao.merge(reader);
		return reader;
	}

	@Override
	public Pagination<?> list(int no, String type) {
		switch (type) {
		case "Book":
			return bookDao.list(no, LIbraryConstant.MAX_RECORDS_PER_PAGE, Book.class);
		case "Category":
			return categoryDao.list(no, LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class);
		case "Reader":
			return readerDao.list(no, LIbraryConstant.MAX_RECORDS_PER_PAGE, Reader.class);
		default:
			throw new LibraryException(LibraryMessages.UNKNOWN_ERROR);
		}
	}

}
