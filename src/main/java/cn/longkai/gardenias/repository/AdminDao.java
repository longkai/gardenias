package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.Admin;

/**
 * 管理员的数据访问接口。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface AdminDao extends GenericDao<Admin> {

	/**
	 * 查找管理员账号
	 * @param username
	 * @param password
	 */
	Admin find(String username, String password);
	
}
