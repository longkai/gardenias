package cn.longkai.gardenias.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * 自定义的一个拦截器，如果非法用户试图未登录访问用户的信息页面，或者登陆后的用户超时了，则拦截掉请求，退到登陆页面。
 * @author longkai
 * @since 2013-01-07
 */
public class LoginIntercepter extends HandlerInterceptorAdapter {

	private static final Logger	l = LoggerFactory.getLogger(LoginIntercepter.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("r") == null) {
			l.info("未登录用户，或者session过期用户试图访问用户信息和图书资源页面，拦截成功！");
			response.sendRedirect("/resources/html/login.html");
			return false;
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
	}

}
