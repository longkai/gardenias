package cn.longkai.gardenias.service;

import java.io.Serializable;

import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.util.Pagination;

/**
 * 接口，用来抓取读者相应的信息列表（预约，借阅，罚款）
 * @author im.longkai
 *
 * @param <T> 泛型
 */
public interface FetchInfo {

	/**
	 * 根据相应的类型，返回对应用户的信息（分页）
	 * @param type
	 * @param reader
	 * @param NO
	 */
	Pagination<?> fetch(String type, Reader reader, int NO);
	
//	由于页面可以直接传递info的id，所以这个也就可以没必要了，用下面那个-_-
	/**
	 * 根据
	 * @param type
	 * @param reader
	 * @param book
	 * @return
	 */
//	<X> X fetch(String type, Reader reader, Book book);
	
	/**
	 * 根据类型和主键，返回信息
	 * @param type
	 * @param id
	 */
	Object fetch(String type, Serializable id);
	
}
