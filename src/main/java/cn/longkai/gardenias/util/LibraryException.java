package cn.longkai.gardenias.util;

/**
 * 图书馆里系统异常，注意，继承的是运行时异常！（避免冗余的异常处理）。
 * 
 * @author longkai
 * @since 2012-12-29
 */
public class LibraryException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public LibraryException() {
		super();
	}

	public LibraryException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public LibraryException(String message, Throwable cause) {
		super(message, cause);
	}

	public LibraryException(String message) {
		super(message);
	}

	public LibraryException(Throwable cause) {
		super(cause);
	}
	
}
