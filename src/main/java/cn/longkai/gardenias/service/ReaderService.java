package cn.longkai.gardenias.service;

import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.util.Pagination;

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
	
	/**
	 * 读者修改个人信息
	 * @param reader
	 */
	Reader update(Reader reader);
	
}
