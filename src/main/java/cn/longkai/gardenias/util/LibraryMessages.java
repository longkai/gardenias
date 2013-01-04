package cn.longkai.gardenias.util;

/**
 * 记录一些常用的字符串-_-.
 * 
 * @author longkai
 * @since 2012-12-29
 */
public class LibraryMessages {

	/** 账号已经被注册过了 */
	public static final String ACCOUNT_HAS_BEEN_REGISTERED = "对不起，改账号已经被注册过啦！";
	
	/** 登录失败 */
	public static final String LOGIN_FAIL = "对不起，登录失败！";
	
	/** 由于有欠款，操作失败 */
	public static final String FAIL_FOR_HAS_CHARGES = "对不起，您有欠款未交付，操作失败！";
	
	/** 由于达到了最大一本书的预约数量，预约失败 */
	public static final String FAIL_FOR_REACH_MAX_BOOKED_TIMES = "对不起，这本书已经达到了最大预约数量，预约失败！";
	
	/** 因为有剩余图书，预约图书失败 */
	public static final String BOOKING_FAIL_FOR_HAS_BOOKS_REMAIN = "对不起，这本书还未被借阅，您可以借阅，不需要预约！";
	
	/** 由于预约的图书数目已经达到最大值，预约图书失败 */
	public static final String BOOKING_FAIL_FOR_MAX_BOOKING_NUMBER = "对不起，您所预约的图书数目已经达到最大值，预约失败！";
	
	/** 不允许重复预约 */
	public static final String BOOKING_FAIL_FOR_THE_SAME_BOOK = "对不起，您已经预约过这本书了，不允许重复预约！";
	
	/** 由于预约图书被借光了，借阅图书失败 */
	public static final String LEND_FAIL_FOR_NONE_BOOKS = "对不起，您想借阅的图书已经被全部借出，请等待其返还！";
	
	/** 借阅的图书数目已经达到最大值，借阅失败 */
	public static final String LEND_FAIL_FOR_MAX_BOOK_BORROWED = "对不起，您所借阅的图书数目已经达到最大值，借阅失败！";
	
	/** 借阅的图书已经被预定了，借书失败 */
	public static final String LEND_FAIL_FOR_HAS_BEEN_BOOKED = "对不起，您所借阅的图书已经被预定了，借书失败！";
	
	/** 同一本书不能借阅两次 */
	public static final String LEND_FAIL_FOR_HAS_BEEN_LENDED_BY_SELF = "对不起，您已经借阅过该书了，不能借阅同一本书！";
	
	/** 读者没有欠款 */
	public static final String NO_CHARGE = "您没有欠款！"; 
	
	/** 不允许借阅 */
	public static final String NOT_ALLOWED_LEND = "对不起，这本书不外借！";
	
	/** 无权限 */
	public static final String PERMISSION_DENIED = "对不起，您没有权限！";
	
	/** 未知的错误 */
	public static final String UNKNOWN_ERROR = "未知的错误，请检查您的操作或者联系管理员！";
	
}
