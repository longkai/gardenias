package cn.longkai.gardenias.service;

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
	
}
