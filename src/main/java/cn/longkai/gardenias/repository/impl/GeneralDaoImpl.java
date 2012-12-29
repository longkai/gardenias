package cn.longkai.gardenias.repository.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import cn.longkai.gardenias.repository.GenericDao;
import cn.longkai.gardenias.util.Pagination;

/**
 * 通用的数据访问抽象类。
 * 
 * @author longkai
 * @param <T> 泛型
 * @since 2012-12-29
 */
public abstract class GeneralDaoImpl<T> implements GenericDao<T> {

	@PersistenceContext
	protected EntityManager em;
	
	protected static final Logger l = LoggerFactory.getLogger(GeneralDaoImpl.class);
	
	@Override
	public T persist(T entity) {
		Assert.notNull(entity, "实体不能为空！");
		em.persist(entity);
		return entity;
	}

	@Override
	public T merge(T entity) {
		Assert.notNull(entity, "实体不能为空！");
		em.merge(entity);
		return null;
	}

	@Override
	public T remove(T entity) {
		Assert.notNull(entity, "实体不能为空！");
		em.remove(entity);
		return entity;
	}

	@Override
	public T find(Serializable pk, Class<T> type) {
		return em.find(type, pk);
	}

	@Override
	public int size(Class<T> type) {
		l.debug("要查找的类型是{}", type.getSimpleName());
//		TODO: 使用更好的方式，SIZE设置成public static final, 但是，参数插入不进去。。。
		String hql = "SELECT COUNT(t.id) FROM ? AS t".replace("?", type.getSimpleName());
		Long recordSize = em.createQuery(hql, Long.class)
//				.setParameter(1, type.getSimpleName())
				.getSingleResult();
		return recordSize.intValue();
	}

	@Override
	public Pagination<T> list(int NO, int howMany, Class<T> type) {
		Pagination<T> p = new Pagination<>(NO, howMany, this.size(type));
		String hql = "from " + type.getSimpleName();
		List<T> list = em.createQuery(hql, type)
				.setFirstResult(p.getBeginRow())
				.setMaxResults(howMany)
				.getResultList();
		return p.setList(list);
	}
	
}
