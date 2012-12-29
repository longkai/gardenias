package cn.longkai.gardenias.repository;

import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.Reader;

/**
 * 借书超期罚款数据访问接口。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public interface ChargeInfoDao extends GenericDao<ChargeInfo> {

	/**
	 * 获取读者有多少条<b style="color: red;">未</b>付款记录。
	 * @param reader 要查阅的读者
	 * @return 记录条数
	 */
	int howManyChargeBooks(Reader reader);
	
}
