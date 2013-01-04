package cn.longkai.gardenias.repository.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.ChargeInfoDao;
import cn.longkai.gardenias.util.LibraryUtil;

/**
 * 图书借阅超期罚款记录。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class ChargeInfoDaoImpl extends GeneralDaoImpl<ChargeInfo> implements ChargeInfoDao {

	/** 查询未交付罚款的记录数 */
	private static final String SELECT_CHARGED_COUNT 
		= "SELECT COUNT(ci.id) FROM ChargeInfo ci where ci.reader = ?";
	
	
	/** 查询一条确定的罚款记录 */
	private static final String SELECT_CHARGEINFO_BY_BOOK_AND_READER 
		= "FROM ChargeInfo ci WHERE ci.book = :book AND ci.reader = :reader AND ci.chargeDate IS null";
	
	@Override
	public int howManyChargedBooks(Reader reader) {
		Long counter = em.createQuery(SELECT_CHARGED_COUNT, Long.class)
				.setParameter(1, reader)
				.getSingleResult();
		return counter.intValue();
	}

	@Override
	public ChargeInfo find(Book book, Reader reader) {
		Map<String, Object> map = LibraryUtil.getNameAndValueMap(book, reader);
		ChargeInfo chargeInfo = null;
		try {
			chargeInfo = super.executeQuery(SELECT_CHARGEINFO_BY_BOOK_AND_READER, ChargeInfo.class, map);
		} catch (Exception e) {
			l.error("罚款记录：{} 查询时异常 -〉".replace("{}", book.getTitle()), e);
			chargeInfo = null;
		}
		return chargeInfo;
	}

}
