package cn.longkai.gardenias.service;

import cn.longkai.gardenias.entity.Admin;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.util.Pagination;

/**
 * 管理员的对外服务接口
 * 
 * @author longkai
 * @since 2013-01-11
 * @version 1.0
 */
public interface AdminService {
	
	/**
	 * 管理员登陆
	 * @param admin
	 */
	Admin login(Admin admin);

	/**
	 * 添加一本书
	 * @param book
	 */
	Book add(Book book);

	/**
	 * 添加一个类别
	 * @param category
	 */
	Category add(Category category);
	
	/**
	 * 更新一个图书
	 * @param book
	 */
	Book update(Book book);
	
	/**
	 * 更新一个类别
	 * @param category
	 */
	Category update(Category category);

	/**
	 * 更新一个用户信息
	 * @param reader
	 */
	Reader update(Reader reader);	

	/**
	 * 获取列表
	 * @param no
	 * @param type
	 */
	Pagination<?> list(int no, String type);
	
}
