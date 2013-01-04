package cn.longkai.gardenias.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;

/**
 * 一些实用的静态方法。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public class LibraryUtil {

//	这里的时间都是按照图书馆的标准来的，借书一月就30天，别搞混淆了0.0
//	注意long假如由int互相乘，很可能会溢出，所以至少乘数里面有一个long类型的值！！！
	public static final String NOT_NULL = "对象不能为空！";
	
	public static final int ONE_MONTH_DAYS = 30;
	
	public static final int ONE_DAY_HOURS = 24;
	
	public static final int ONE_HOUR_MINUTES = 60;
	
	public static final int ONE_MINUTE_SECONDS = 60;
	
	public static final long ONE_SECOND_MILLIS = 1000;
	
	public static final long ONE_MONTH_MILLIS 
		= ONE_MONTH_DAYS * ONE_DAY_HOURS * ONE_HOUR_MINUTES 
			* ONE_MINUTE_SECONDS * ONE_SECOND_MILLIS;
	
	public static final long ONE_DAY_MILLIS = ONE_MONTH_MILLIS / ONE_MONTH_DAYS;
	
	/** 默认的日期格式 */
	private static final String DEFAULT_DATE_FOMAT_PATTERN = "yyyy-DD-mm HH:mm:ss";

	/**
	 * 监测是否为空。
	 * @param objects
	 */
	public static void checkNull(Object...objects) {
//		对于可变长参数列表，假如就只有一个参数且为空，那么整个（数组）为空，如果有多个参数为null，那么数组为非null！
//		if (objects == null) {
//			System.out.println("objects == null");
//		}
		for (Object object : objects) {
			Assert.notNull(object, NOT_NULL);
		}
	}
	
	/**
	 * 是否过期，与当前时间相比，此处使用了自定义的算法，比较简单-_-。
	 * @param date 待比较的时间
	 * @param rangeDays 比较的时间跨度
	 */
	public static boolean runout(Date date, int rangeDays) {
		long gap = calculateMillisGap(date);
		long millisOfRange = rangeDays * ONE_DAY_MILLIS;
		return gap > millisOfRange ? true : false;
	}
	
	/**
	 * 获取一个book=book, reader=reader的hashmap。
	 * @param book
	 * @param reader
	 */
	public static Map<String, Object> getNameAndValueMap(Book book, Reader reader) {
		Map<String, Object> map = new HashMap<>();
		map.put("book", book);
		map.put("reader", reader);
		return map;
	}
	
	/**
	 * 返回距离当前时间的世界差，毫秒。
	 * @param date
	 * @param rangeDays
	 */
	public static long calculateMillisGap(Date date) {
		return System.currentTimeMillis() - date.getTime();
	}
	
	/**
	 * 返回距离当前时间的世界差，天数。
	 * @param date
	 * @param rangeDays
	 */
	public static long calculateDaysGap(Date date) {
		long gap = calculateMillisGap(date);
		return gap / ONE_DAY_MILLIS;
	}
	
	/**
	 * 他是否需要罚款？
	 * @param lendInfo
	 */
	public static boolean doesItCharged(LendInfo lendInfo) {
		return calculateDaysGap(lendInfo.getLendDate()) > LIbraryConstant.MAX_LEND_DATE ? true : false;
	}
	
	/**
	 * 获取当前时间的字符串
	 * @return
	 */
	public static String getCurrentTime() {
		return new SimpleDateFormat(DEFAULT_DATE_FOMAT_PATTERN).format(new Date());
	}

}
