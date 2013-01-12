package cn.longkai.gardenias.controller;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
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
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.entity.ChargeInfo;
import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.repository.CategoryDao;
import cn.longkai.gardenias.service.FetchInfo;
import cn.longkai.gardenias.service.LibraryService;
import cn.longkai.gardenias.service.ReaderService;
import cn.longkai.gardenias.service.SearchService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.LibraryMessages;
import cn.longkai.gardenias.util.LibraryUtil;
import cn.longkai.gardenias.util.Pagination;

@Controller
@RequestMapping("/reader")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class ReaderController {

	private static final Logger	l	= LoggerFactory
											.getLogger(ReaderController.class);

	@Inject
	private ReaderService		readerService;

	@Inject
	private LibraryService		libraryService;

	@Inject
	private CategoryDao			categoryDao;

	@Inject
	private BookDao				bookDao;

	@Inject
	private FetchInfo			fetchInfo;

	@Inject
	private SearchService		searchService;

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
	public ModelAndView login(HttpServletRequest request, String account,
			String password) {
		l.debug("用户登陆，账号：{}，密码：{}", account, password);
		Reader reader = readerService.login(account, password);
		ModelAndView mav = new ModelAndView();
		l.debug("登陆成功！用户昵称：{}", reader.getNick());
		HttpSession session = request.getSession();
		session.setAttribute("r", reader);
		mav.addObject("reader", reader);
		mav.addObject("categories", categoryDao.list(1,
				LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
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
		model.addAttribute("reader", request.getSession(false)
				.getAttribute("r"));
		model.addAttribute("categories", categoryDao.list(1,
				LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		return "main";
	}

	@RequestMapping(value = "/zone/{type}", produces = "application/json;charset=utf-8")
	public @ResponseBody
	String zone(HttpSession session, @PathVariable String type,
			@RequestParam("no") int NO) {
		Reader reader = (Reader) session.getAttribute("r");
		Pagination<?> p = fetchInfo.fetch(type, reader, NO);
		List<?> list = p.getList();
		l.debug("user:{}, no:{}, type:{}, total:{}, sum:{}", reader.getName(),
				NO, type, p.getTotalRecords(), p.getTotal());
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

	@RequestMapping("/view_info/{type}")
	public String viewInfo(HttpSession session, @PathVariable String type,
			@RequestParam int id, Model model) {
		Object info = fetchInfo.fetch(type, id);
		model.addAttribute("info", info);
		if (type.equals("LendInfo")) {
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(((LendInfo) info).getDate().getTime()
					+ LibraryUtil.ONE_MONTH_MILLIS);
			model.addAttribute("returnDate", c.getTime());
		}
		return "view_info";
	}

	@RequestMapping("charge")
	public String charge(HttpSession session, @RequestParam("book_id") int id,
			Model model) {
		// ChargeInfo info = (ChargeInfo) fetchInfo.fetch("ChargeInfo", id);
		Reader reader = (Reader) session.getAttribute("r");
		// if (!reader.equals(info.getReader())) {
		// throw new LibraryException(LibraryMessages.UNKNOWN_ERROR);
		// }
		Book book = bookDao.find(id, Book.class);
		ChargeInfo info = libraryService.charge(book, reader);
		model.addAttribute("info", info);
		model.addAttribute("msg", "缴纳欠款成功！");
		return "ok";
	}

	@RequestMapping("/_return")
	public String _return(HttpSession session, @RequestParam("lend_id") int id,
			Model model) {
		LendInfo info = (LendInfo) fetchInfo.fetch("LendInfo", id);
		Reader reader = (Reader) session.getAttribute("r");
		if (!reader.equals(info.getReader())) {
			throw new LibraryException(LibraryMessages.UNKNOWN_ERROR);
		}
		libraryService._return(info.getBook(), reader);
		model.addAttribute("info", info);
		model.addAttribute("msg", "归还图书成功！");
		return "ok";
	}

	@RequestMapping("/cancel")
	public String cancel(HttpSession session,
			@RequestParam("booking_id") int id, Model model) {
		BookingInfo info = (BookingInfo) fetchInfo.fetch("BookingInfo", id);
		Reader reader = (Reader) session.getAttribute("r");
		l.debug("r1.id:{}", reader.getId());
		l.debug("r2.id:{}", info.getReader().getId());
		if (!reader.equals(info.getReader())) {
			throw new LibraryException(LibraryMessages.UNKNOWN_ERROR);
		}
		info = libraryService.cancel(info.getBook(), reader);
		model.addAttribute("info", info);
		model.addAttribute("msg", "取消预约图书成功!");
		return "ok";
	}

	@RequestMapping("/search/{type}")
	public String search(@PathVariable int type, @RequestParam("no") int NO,
			String keywords, Model model) {
		// TODO: 这里可以使用枚举来完成
		l.debug("搜索的类型是：{}", type == 1 ? "标题" : "作者"	);
		switch (type) {
		case 1:
			model.addAttribute("p", searchService.searchByTile(keywords, NO));
			break;
		case 2:
			model.addAttribute("p", searchService.searchByAuthor(keywords, NO));
			break;
		default:
			throw new LibraryException(LibraryMessages.UNKNOWN_ERROR);
		}
		return "result";
	}

}
