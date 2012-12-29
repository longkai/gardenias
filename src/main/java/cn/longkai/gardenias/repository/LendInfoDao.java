package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;

/**
 * 图书借阅信息数据访问接口。
 * @author longkai
 * @since 2012-12-29
 */
public interface LendInfoDao extends GenericDao<LendInfo> {

	/**
	 * 该读者借阅图书（<b style="color: red">尚未归还</b>）的数量。
	 * @param reader
	 * @return
	 */
	int howManyLendedBooks(Reader reader);
	
}
