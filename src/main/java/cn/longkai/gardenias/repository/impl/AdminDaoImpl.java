package cn.longkai.gardenias.repository.impl;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.Admin;
import cn.longkai.gardenias.repository.AdminDao;

/**
 * 管理员数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class AdminDaoImpl extends GeneralDaoImpl<Admin> implements AdminDao {

	/** 查找一个认证的管理员 */
	private static final String QUERY_FOR_ADMIN 
		= "FROM Admin a where a.username = ? and a.password = ?";
	
	@Override
	public Admin find(String username, String password) {
		return super.executeQuery(QUERY_FOR_ADMIN, Admin.class, username, password);
	}

}
