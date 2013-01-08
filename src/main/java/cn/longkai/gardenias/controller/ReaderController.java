package cn.longkai.gardenias.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Admin;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.CategoryDao;
import cn.longkai.gardenias.service.FetchInfo;
import cn.longkai.gardenias.service.ReaderService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.LibraryMessages;
import cn.longkai.gardenias.util.Pagination;

@Controller
@RequestMapping("/reader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReaderController {
	
	private static final Logger l = LoggerFactory.getLogger(ReaderController.class);
	
	@Inject
	private ReaderService readerService;
	
	@Inject
	private CategoryDao categoryDao;
	
	@Inject
	private FetchInfo fetchInfo;

	@ExceptionHandler
	public ModelAndView exception(Exception e) {
		ModelAndView mv = new ModelAndView("error");
		l.error("error!", e);
		mv.addObject("msg", "出错啦，请记住当前时间与错误信息以便及时联系管理员！ 错误信息：" + e.getMessage());
		return mv;
	}
	
	@ExceptionHandler
	public ModelAndView exception(LibraryException e) {
		ModelAndView mv = new ModelAndView("error");
		l.error("error!", e);
		mv.addObject("msg", e.getMessage());
		return mv;
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, String account, String password) {
		l.debug("用户登陆，账号：{}，密码：{}", account, password);
		Reader reader = readerService.login(account, password);
		ModelAndView mav = new ModelAndView();
		l.debug("登陆成功！用户昵称：{}", reader.getNick());
		HttpSession session = request.getSession();
		session.setAttribute("r", reader);
		mav.addObject("reader", reader);
		mav.addObject("categories", categoryDao.list(1, LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView register(Reader reader, String password2) {
		l.debug("新用户注册！账号：{}", reader.getAccount());
		ModelAndView mav = new ModelAndView();
		if (!reader.getPassword().equals(password2)) {
			throw new LibraryException("两次密码不一致!");
		}
		readerService.register(reader);
		mav.addObject("reader", reader);
		mav.setViewName("main");
		return mav;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		session.invalidate();
		model.addAttribute("msg", "安全退出！");
		return "login";
	}
	
	@RequestMapping("/self_info")
	public ModelAndView viewReaderInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Reader reader = (Reader) session.getAttribute("r");
		ModelAndView mav = new ModelAndView();
		mav.addObject("reader", reader);
		mav.setViewName("self_info");
		return mav;
	}
	
	@RequestMapping(value = "/update_self_info", method = RequestMethod.POST)
	public ModelAndView update(Reader reader, HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		Reader originalReader = (Reader) session.getAttribute("r");
		if (!originalReader.equals(reader)) {
			throw new LibraryException(LibraryMessages.UNKNOWN_ERROR);
		}
		originalReader.setEmail(reader.getEmail());
		originalReader.setAge(reader.getAge());
		originalReader.setNick(reader.getNick());
		readerService.update(originalReader);
		session.setAttribute("r", originalReader);
		ModelAndView mav = new ModelAndView();
		mav.addObject("reader", originalReader);
		mav.addObject("msg", "修改信息成功！");
		mav.setViewName("self_info");
		return mav;
	}
	
	@RequestMapping("/main")
	public String main(HttpServletRequest request, Model model) {
		model.addAttribute("reader", request.getSession(false).getAttribute("r"));
		model.addAttribute("categories", categoryDao.list(0, LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		return "main";
	}
	
	@RequestMapping(value = "/zone/{type}", produces = "application/json;charset=utf-8")
	public @ResponseBody String zone(HttpSession session, @PathVariable String type, @RequestParam("no") int NO) {
		Reader reader = (Reader) session.getAttribute("r");
		Pagination<?> p = fetchInfo.fetch(type, reader, NO);
		List<?> list = p.getList();
		l.debug("user:{}, no:{}, type:{}, total:{}, sum:{}", reader.getName(), NO, type, p.getTotalRecords(), p.getTotal());
		if (list == null || list.size() == 0) {
			return null;
		}
		JSONArray json = new JSONArray(list);
		return json.toString();
	}
	
	@RequestMapping("/zone")
	public String zone() {
		
		return "zone";
	}

}
