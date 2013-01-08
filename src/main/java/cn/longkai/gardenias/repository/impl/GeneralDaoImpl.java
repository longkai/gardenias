package cn.longkai.gardenias.repository.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.Reader;
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
//	
//	protected int size(String hql) {
//		em.
//	}

	@Override
	public Pagination<T> list(int NO, int howMany, Class<T> type) {
		Pagination<T> p = new Pagination<>(NO, howMany, this.size(type));
//		如果用户加载的页码已经超过了最大页码，那就不加载啦！
		if (p.getTotal() < NO) {
			return p;
		}
		String hql = "from " + type.getSimpleName() + " t order by t.id desc";
		List<T> list = em.createQuery(hql, type)
				.setFirstResult(p.getBeginRow())
				.setMaxResults(howMany)
				.getResultList();
		return p.setList(list);
	}
	
	/**
	 * 执行一个对象的查询，只返回一个对象，支持<b style="color: red;">?</b>格式来作为占位符的格式。
	 * @param hql 预处理hql
	 * @param type 返回的对象类型
	 * @param objects 参数, 为null表示无占位符
	 */
	protected <X> X executeQuery(String hql, Class<X> type, Object...objects) {
		TypedQuery<X> query = this.getQuery(hql, type, 0, 1);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i + 1, objects[i]);
			}
		}
		return query.getSingleResult();
	}
	
	/**
	 * 执行一个对象的查询，只返回一个对象，支持支<b style="color: red;">:name</b>格式来作为占位符的格式。
	 * @param hql 预处理hql
	 * @param type 返回的对象类型
	 * @param nameAndValue 参数名和参数值, 为null表示无占位符
	 */
	protected <X> X executeQuery(String hql, Class<X> type, Map<String, Object> nameAndValue) {
		TypedQuery<X> query = this.getQuery(hql, type, 0, 1);
		if (nameAndValue != null) {
			for (String name : nameAndValue.keySet()) {
				query.setParameter(name, nameAndValue.get(name));
			}
		}
		return query.getSingleResult();
	}
	
	/**
	 * 执行一个列表查询，支持<b style="color: red;">?</b>格式来作为占位符的格式。
	 * @param hql
	 * @param type
	 * @param begin
	 * @param size
	 * @param objects
	 */
	protected <X> List<X> queryForList(String hql, Class<X> type, int begin, int size, Object...objects) {
		TypedQuery<X> query= this.getQuery(hql, type, begin, size);
		if (objects != null) {
			for (int i = 0; i < objects.length; i++) {
				query.setParameter(i + 1, objects[i]);
			}
		}
		return query.getResultList();
	}
	
	/**
	 * 执行一个列表查询，支持支<b style="color: red;">:name</b>格式来作为占位符的格式。
	 * @param hql
	 * @param type
	 * @param begin
	 * @param size
	 * @param map
	 * @return
	 */
	protected List<?> queryForList(String hql, Class<?> type, int begin, int size, Map<String, Object> map) {
		TypedQuery<?> query= this.getQuery(hql, type, begin, size);
		if (map != null) {
			for (String name : map.keySet()) {
				query.setParameter(name, map.get(name));
			}
		}
		return query.getResultList();
	}
	
	/**
	 * 私有方法，初始化查询
	 * @param <X>
	 * @param hql
	 * @param type
	 * @param begin
	 * @param size
	 */
	private <X> TypedQuery<X> getQuery(String hql, Class<X> type, int begin, int size) {
		return em.createQuery(hql, type)
				.setFirstResult(begin)
				.setMaxResults(size);
	}
	
	/**
	 * 返回信息列表
	 * @param type
	 * @param hql
	 * @param reader
	 * @param offset
	 * @param size
	 */
	protected Pagination<T> queryForList(Class<T> type, String hql, Reader reader, int offset, int size) {
		int totalRecords = this.executeQuery("SELECT COUNT(t.id) " + hql, Long.class, reader).intValue();
		Pagination<T> p = new Pagination<>(offset, size, totalRecords);
		if (p.getTotal() < offset) {
			return p;
		}
		List<T> list = this.queryForList(hql, type, p.getBeginRow(), size, reader);
		return p.setList(list);
	}
	
}
