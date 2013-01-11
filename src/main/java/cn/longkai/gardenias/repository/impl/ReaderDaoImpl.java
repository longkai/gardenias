package cn.longkai.gardenias.repository.impl;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.ReaderDao;

/**
 * 读者的数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class ReaderDaoImpl extends GeneralDaoImpl<Reader> implements ReaderDao {

	@Override
	public Reader find(String account, String password) {
		l.info("账户：{}，密码{}", account, password);
		String hql = "from Reader r where r.account = :account and r.password = :password";
		Reader reader = em.createQuery(hql, Reader.class)
				.setParameter("account", account)
				.setParameter("password", password)
				.getSingleResult();
		return reader;
	}

	@Override
	public boolean exists(String account) {
		String hql = "SELECT COUNT(r.id) from Reader r WHERE r.account = :account";
		Long howMany = em.createQuery(hql, Long.class)
				.setParameter("account", account)
				.getSingleResult();
		l.debug("账号{}的数量是{}", account, howMany);
		return howMany > 0 ? true : false;
	}

	@Override
	public Reader remove(Reader entity) {
		String delete1 = "delete from BookingInfo bi where bi.reader = ?";
		String delete2 = "delete from ChargeInfo ci where ci.reader = ?";
		String delete3 = "delete from LendInfo li where li.reader = ?";
		em.createQuery(delete1).setParameter(1, entity).executeUpdate();
		em.createQuery(delete2).setParameter(1, entity).executeUpdate();
		em.createQuery(delete3).setParameter(1, entity).executeUpdate();
		return super.remove(entity);
	}
	
}
