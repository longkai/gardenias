package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.Reader;

/**
 * 读者的数据访问接口
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface ReaderDao extends GenericDao<Reader> {

	/**
	 * 判断读者的账号和密码是否一致。
	 */
	Reader find(String account, String password);
	
	/**
	 * 判断读者的账号是否存在。
	 */
	boolean exists(String account);
	
}
