package cn.longkai.gardenias.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Scope("prototype")
public class IndexController {
	
	@RequestMapping({"/", "/index"})
	public String index(HttpServletRequest request) {
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("salutation", "hello!");
//		mav.setViewName("hello");
//		mav.addObject("msg", "成功退出！");
//		mav.addObject("reader", request.getSession(false).getAttribute("r"));
		return "login";
	}
	
	@RequestMapping({"/admin", "/admin/"})
	public String adminIndex() {
		return "admin/login";
	}
	
}