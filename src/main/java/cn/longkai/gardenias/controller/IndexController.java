package cn.longkai.gardenias.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.service.ReaderService;
import cn.longkai.gardenias.util.LibraryException;

@Controller
@Scope("prototype")
public class IndexController {
	
	private static final Logger l = LoggerFactory.getLogger(IndexController.class);
	
	@Inject
	private ReaderService readerService;

	@RequestMapping({"/", "/index"})
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
		mav.addObject("salutation", "hello!");
		mav.setViewName("hello");
		return mav;
	}
	
	@ExceptionHandler(value = LibraryException.class)
	public String error() {
		return "error";
	}
	
	@RequestMapping("/login")
	public ModelAndView login(String account, String password) {
		l.debug("用户登陆，账号：{}，密码：{}", account, password);
		Reader reader = readerService.login(account, password);
		ModelAndView mav = new ModelAndView();
		l.debug("登陆成功！用户昵称：{}", reader.getNick());
		mav.addObject("reader", reader);
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping("/register")
	public ModelAndView register(Reader reader, String password2) {
		l.debug("新用户注册！账号：{}", reader.getAccount());
		ModelAndView mav = new ModelAndView();
		if (!reader.getPassword().equals(password2)) {
			throw new LibraryException();
		}
		readerService.register(reader);
		mav.addObject("reader", reader);
		mav.setViewName("main");
		return mav;
	}
	
}