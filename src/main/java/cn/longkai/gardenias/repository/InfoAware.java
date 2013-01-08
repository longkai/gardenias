package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.util.Pagination;

/**
 * 一个特殊的数据访问接口，根据<b style="color: red;">用户和读者</b>来找到有效的记录信息（包括<b style="color: red;">预约，借书，欠款</b>）。
 * 
 * @author longkai
 * @param <T>
 * @since 2012-12-30
 */
public interface InfoAware<T> {

	/**
	 * 根据<b style="color: red;">用户和读者</b>来找到有效的记录信息（包括<b style="color: red;">预约，借书，欠款</b>）。
	 * @param book
	 * @param reader
	 */
	T find(Book book, Reader reader);
	
	/**
	 * 返回最新的某个用户信息列表，有效无效均可。
	 * @param offset
	 * @param reader
	 * @param size
	 */
	Pagination<T> list(Reader reader, int offset, int size);
}
