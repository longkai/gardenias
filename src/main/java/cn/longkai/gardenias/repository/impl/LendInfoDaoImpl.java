package cn.longkai.gardenias.repository.impl;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.LendInfoDao;

/**
 * 图书借阅信息的数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class LendInfoDaoImpl extends GeneralDaoImpl<LendInfo> implements LendInfoDao {

	private static final String SELECT_LENDED_BOOKS_NUMBER
		= "SELECT COUNT(li.id) FROM LendInfo li WHERE li.reader = ? AND li.returnDate = null";
	
	@Override
	public int howManyLendedBooks(Reader reader) {
		Long counter = em.createQuery(SELECT_LENDED_BOOKS_NUMBER, Long.class)
				.setParameter(1, reader)
				.getSingleResult();
		return counter.intValue();
	}

}
