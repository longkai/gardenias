package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;

/**
 * 图书借阅信息数据访问接口。
 * @author longkai
 * @since 2012-12-29
 */
public interface LendInfoDao extends GenericDao<LendInfo>, InfoAware<LendInfo> {

	/**
	 * 该读者借阅图书（<b style="color: red">尚未归还</b>）的数量。
	 * @param reader
	 * @return
	 */
	int howManyLendedBooks(Reader reader);

//	这里也扩展了接口-_-
//	/**
//	 * 返回<b style="color: red;">尚未归还的</b>这条借阅记录。
//	 * @param book
//	 * @param reader
//	 */
//	LendInfo find(Book book, Reader reader);
	
}
