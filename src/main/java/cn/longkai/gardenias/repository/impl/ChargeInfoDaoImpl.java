package cn.longkai.gardenias.repository.impl;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.ChargeInfoDao;

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
	
	@Override
	public int howManyChargeBooks(Reader reader) {
		Long counter = em.createQuery(SELECT_CHARGED_COUNT, Long.class)
				.setParameter(1, reader)
				.getSingleResult();
		return counter.intValue();
	}

}
