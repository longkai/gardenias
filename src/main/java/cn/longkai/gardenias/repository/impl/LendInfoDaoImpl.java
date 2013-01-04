package cn.longkai.gardenias.repository.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.LendInfoDao;
import cn.longkai.gardenias.util.LibraryUtil;

/**
 * 图书借阅信息的数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class LendInfoDaoImpl extends GeneralDaoImpl<LendInfo> implements LendInfoDao {

	/** 查询用户尚未归还的书籍数量 */
	private static final String SELECT_LENDED_BOOKS_NUMBER
		= "SELECT COUNT(li.id) FROM LendInfo li WHERE li.reader = ? AND li.returnDate = null";
	
	/** 查询用户尚未归还的该书借阅信息 */
	private static final String SELECT_LENDED_BOOK_BY_BOOK_WITH_READER
		= "FROM LendInfo li WHERE li.reader = :reader and li.book = :book and li.returnDate IS null";
	
	@Override
	public int howManyLendedBooks(Reader reader) {
		Long counter = em.createQuery(SELECT_LENDED_BOOKS_NUMBER, Long.class)
				.setParameter(1, reader)
				.getSingleResult();
		return counter.intValue();
	}

	@Override
	public LendInfo find(Book book, Reader reader) {
		Map<String, Object> map = LibraryUtil.getNameAndValueMap(book, reader);
		LendInfo info = null;
		try {
			info = super.executeQuery(SELECT_LENDED_BOOK_BY_BOOK_WITH_READER, LendInfo.class, map);
		} catch (Exception e) {
			l.error("图书:?, 查询是否被借阅时异常".replace("?", book.getTitle()), e);
			info = null;
		}
		return info;
	}
	
}
