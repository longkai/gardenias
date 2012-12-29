package cn.longkai.gardenias.util;

import java.util.Date;

import org.springframework.util.Assert;

/**
 * 一些实用的静态方法。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public class LibraryUtil {

	public static final String NOT_NULL = "对象不能为空！";
	
	public static final int ONE_MONTH_DAYS = 30;
	
	public static final int ONE_DAY_HOURS = 24;
	
	public static final int ONE_HOUR_MINUTES = 60;
	
	public static final int ONE_MINUTE_SECONDS = 60;
	
	public static final int ONE_SECOND_MILLIS = 1000;
	
	public static final long ONE_MONTH_MILLiS 
		= ONE_MONTH_DAYS * ONE_DAY_HOURS * ONE_HOUR_MINUTES 
			* ONE_MINUTE_SECONDS * ONE_SECOND_MILLIS;

	/**
	 * 监测是否为空。
	 * @param objects
	 */
	public static void checkNull(Object...objects) {
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
		long millisOfNow = System.currentTimeMillis();
		long gap = millisOfNow - date.getTime();
		return gap > ONE_MONTH_MILLiS ? true : false;
	}

}
