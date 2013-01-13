package cn.longkai.gardenias.controller;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.BookingInfo;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.entity.LendInfo;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.repository.CategoryDao;
import cn.longkai.gardenias.service.LibraryService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.Pagination;

@Controller
@RequestMapping("/lib")
@Scope("prototype")
public class LibraryController {

	private static final Logger	l	= LoggerFactory
											.getLogger(LibraryController.class);

	@Inject
	private LibraryService		libraryService;

	@Inject
	private CategoryDao			categoryDao;

	@Inject
	private BookDao				bookDao;

	// @Inject
	// private InfoAware<LendInfo> infoAware;

	@ExceptionHandler
	public ModelAndView exception(LibraryException e) {
		l.debug("error here!");
		ModelAndView mv = new ModelAndView();
		mv.addObject("msg", e.getMessage());
		// model.addAttribute("msg", e.getMessage());
		// request.setAttribute("msg", e.getMessage());
		mv.setViewName("error");
		return mv;
	}

	@RequestMapping("/view_books")
	public ModelAndView viewBooks(int categoryId, HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		Pagination<Book> p = bookDao.list(0,
				LIbraryConstant.MAX_VALID_BOOKED_NUMBER,
				categoryDao.find(categoryId, Category.class));
		mav.addObject("p", p);
		// LibraryUtil.synchroniz(mav, request);
		mav.addObject("categories", categoryDao.list(1,
				LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		mav.setViewName("view_books");
		return mav;
	}

	@RequestMapping("/book_detail")
	public String bookDetail(int id, Model model) {
		Book book = bookDao.find(id, Book.class);
		model.addAttribute("b", book);
		return "book_detail";
	}

	@RequestMapping("/book")
	public ModelAndView book(int id, HttpServletRequest request)
			throws LibraryException {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(false);
		Reader reader = (Reader) session.getAttribute("r");
		Book book = bookDao.find(id, Book.class);
		BookingInfo bookingInfo = libraryService.book(book, reader);
		mav.addObject("info", bookingInfo);
		mav.addObject("b", book);
		mav.addObject("msg", "恭喜您预约成功！");
		mav.setViewName("ok");
		return mav;
	}

	@RequestMapping("/borrow")
	public ModelAndView borrow(int id, HttpServletRequest request)
			throws LibraryException {
		ModelAndView mav = new ModelAndView();
		HttpSession session = request.getSession(false);
		Reader reader = (Reader) session.getAttribute("r");
		Book book = bookDao.find(id, Book.class);
		LendInfo lendInfo = libraryService.lend(book, reader);
		mav.addObject("info", lendInfo);
		mav.addObject("b", book);
		mav.addObject("msg", "借阅图书成功！");
		mav.setViewName("ok");
		return mav;
	}

}
