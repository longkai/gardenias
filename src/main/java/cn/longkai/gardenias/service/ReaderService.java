package cn.longkai.gardenias.service;

import cn.longkai.gardenias.entity.Reader;

/**
 * 读者的对外服务接口。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface ReaderService {
	
	/**
	 * 读者注册。
	 */
	void register(Reader reader);

	/**
	 * 读者登录。
	 * @param account
	 * @param password 
	 * @return 认证后的读者
	 */
	Reader login(String account, String password);
	
}
