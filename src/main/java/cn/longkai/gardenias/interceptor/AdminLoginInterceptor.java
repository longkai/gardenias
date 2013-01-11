package cn.longkai.gardenias.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 验证管理员是否登陆的拦截器。
 * 
 * @author longkai
 * @since 2013-01-11
 * @version 1.0
 */
public class AdminLoginInterceptor extends HandlerInterceptorAdapter {

	private static final Logger L = LoggerFactory.getLogger(AdminLoginInterceptor.class);
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("a") == null) {
			L.info("管理员未登陆，拦截成功！");
			response.sendRedirect("/resources/html/admin_login.html");
			return false;
		}
		return true;
	}
	
}
