package cn.longkai.gardenias.config;

/**
 * 图书馆的一些常量
 * 
 * @author longkai
 * @since 2012-12-29
 */
public class LIbraryConstant {

	/** 一本书的最大借阅时间 */
	public static final int		MAX_LEND_DATE			= 30;

	/** 一本书最长的预约时间 */
	public static final int		MAX_BOOKING_DATE		= 30;

	/** 一本书多大的有效预定数量 */
	public static final int		MAX_VALID_BOOKED_NUMBER	= 10;

	/** 一本书超期后每天的罚款金额 */
	public static final float	DAILY_CHARGE_FEE		= 0.1F;

	/** 读者最多能借阅的图书数量 */
	public static final int		MAX_LEND_BOOK_NUMBER	= 10;

	/** 读者最多能预定图书的数量 */
	public static final int		MAX_BOOKING_NUMBER		= 10;

	/** 每页显示的最大记录数 */
	public static final int		MAX_RECORDS_PER_PAGE	= 15;

}
