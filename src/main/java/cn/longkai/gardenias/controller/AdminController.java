package cn.longkai.gardenias.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cn.longkai.gardenias.config.LIbraryConstant;
import cn.longkai.gardenias.entity.Admin;
import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.entity.Category;
import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.BookDao;
import cn.longkai.gardenias.repository.CategoryDao;
import cn.longkai.gardenias.repository.ReaderDao;
import cn.longkai.gardenias.service.AdminService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.LibraryUtil;

/**
 * 管理员控制器。
 * 
 * @author longkai
 * @since 2013-01-11
 * @version 1.0
 */
@Controller
@RequestMapping("/admin")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class AdminController {
	
	private static final Logger L = LoggerFactory.getLogger(AdminController.class);

	@Inject
	private AdminService adminService;
	
	@Inject
	private CategoryDao categoryDao;
	
	@Inject
	private BookDao bookDao;
	
	@Inject
	private ReaderDao readerDao;
	
	@ExceptionHandler(LibraryException.class)
	public String handleLibException(Model model, LibraryException e) {
		model.addAttribute("msg", e.getMessage());
		return "error";
	}
	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleException(Exception e) {
		ModelAndView mv = new ModelAndView("error");
		mv.addObject("msg", e.getMessage());
		return mv;
	}
	
//	@RequestMapping("/")
//	public String index() {
//		return "admin/login";
//	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView login(HttpServletRequest request, Admin admin) {
		ModelAndView mv = new ModelAndView("admin/index");
		Admin a = adminService.login(admin);
		HttpSession session = request.getSession();
		L.info((session == null) + "");
		session.setAttribute("a", a);
//		return "admin/index";
		return mv;
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
		session.invalidate();
		redirectAttributes.addAttribute("msg", "安全退出！");
		return "redirect:/admin/{msg}";
	}
	
	@RequestMapping("/list/{type}")
	public String list(@PathVariable String type, @RequestParam("no") int no, Model model) {
		model.addAttribute("p", adminService.list(no, type));
		return "admin/list";
	}
	
	@RequestMapping("/view_category")
	public String category(@RequestParam(value = "id", required = true) int categoryId, Model model) {
		Category c = categoryDao.find(categoryId, Category.class);
		model.addAttribute("c", c);
		return "admin/category_info";
	}
	
	@RequestMapping(value = "/update_category", method = RequestMethod.POST)
	public String update(Category category, Model model, @RequestParam(value = "include_date", required = true) String includeDate) throws ParseException {
		Date d = new SimpleDateFormat(LibraryUtil.ISO_DATE_TIME).parse(includeDate);
		L.info("修改类别！{}", category);
		L.info("日期转换：{}", includeDate);
		category.setIncludeDate(d);
		adminService.update(category);
		model.addAttribute("c", category);
		model.addAttribute("msg", "类别修改成功！");
		return "admin/category_info";
	}
	
	@RequestMapping("/view_book")
	public String book(@RequestParam(value = "id", required = true) int bookId, Model model) {
		Book book = bookDao.find(bookId, Book.class);
		L.debug("查询id:{}要查询的图书信息是：{}", bookId, book);
		model.addAttribute("b", book);
		model.addAttribute("p", categoryDao.list(1, LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		return "admin/book_info";
	}
	
	@RequestMapping(value = "/update_book", method = RequestMethod.POST)
	public String update(Book book, @RequestParam(value = "categoryId", required = true) int categoryId, @RequestParam(value = "publish_date", required = true) String includeDate, Model model) throws ParseException {
		L.debug("修改的图书是：{}，id:{}，日期：{}", book.getTitle(), book.getId(), includeDate);
		Book b = bookDao.find(book.getId(), Book.class);
		Category c = categoryDao.find(categoryId, Category.class);
		Date d = new SimpleDateFormat(LibraryUtil.ISO_DATE_TIME).parse(includeDate);
		b.setIncludedDate(d);
		b.setCategory(c);
		b.setTitle(book.getTitle());
		b.setAuthor(book.getAuthor());
		b.setBorrowedTimes(book.getBorrowedTimes());
		b.setBookedTimes(book.getBookedTimes());
		b.setClickTimes(book.getClickTimes());
		b.setDescription(book.getDescription());
		b.setISBN(book.getISBN());
		b.setLendable(book.isLendable());
		b.setPublishHouse(book.getPublishHouse());
		b.setPrice(book.getPrice());
		b.setTotal(book.getRemain());
		b.setUrl(book.getUrl());
		adminService.update(b);
		model.addAttribute("msg", "图书修改成功！");
		model.addAttribute("p", categoryDao.list(1, LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		model.addAttribute("b", b);
		book = null;
		return "admin/book_info";
	}
	
	@RequestMapping("/view_reader")
	public String reader(@RequestParam(value = "id", required = true) int readerId, Model model) {
		L.debug("读者id：{}", readerId);
		Reader reader = readerDao.find(readerId, Reader.class);
		model.addAttribute("r", reader);
		return "admin/reader_info";
	}
	
	@RequestMapping(value = "/update_reader", method = RequestMethod.POST)
	public String update(Reader reader, Model model) {
		Reader r = readerDao.find(reader.getAccount(), Reader.class);
		L.debug("修改读者账号：{}，昵称：{}", r.getAccount(), r.getNick());
		r.setEmail(reader.getEmail());
		r.setAge(reader.getAge());
//		r.setAddress(address)
		r.setName(reader.getName());
		r.setNick(reader.getNick());
		r.setIdCard(reader.getIdCard());
		r.setSex(reader.isSex());
		adminService.update(r);
		model.addAttribute("r", reader);
		model.addAttribute("msg", "读者修改成功");
		return "admin/reader_info";
	}
	
	@RequestMapping(value = "add_category", method = RequestMethod.POST) 
	public String add(Category category, Model model) {
		adminService.add(category);
		L.info("类别添加成功！id：{}，名称：{}", category.getId(), category.getName());
		model.addAttribute("msg", "类别添加成功！");
		model.addAttribute("c", category);
		return "ok";
	}
	
	@RequestMapping(value = "add_book", method = RequestMethod.POST) 
	public String add(Book book, Model model, @RequestParam(value = "publish_date", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date publishDate, @RequestParam(value = "categoryId", required = true) int categoryId) {
		L.debug("出版时间为：{}", publishDate);
		Category c = categoryDao.find(categoryId, Category.class);
		book.setCategory(c);
		book.setPublishDate(publishDate);
		adminService.add(book);
		L.info("添加图书成功！id：{}，标题：{}", book.getId(), book.getTitle());
		model.addAttribute("msg", "图书添加成功！");
		model.addAttribute("b", book);
		return "ok";
	}
	
	@RequestMapping("/add_book_request")
	public String addBook(Model model) {
		model.addAttribute("p", categoryDao.list(1, LIbraryConstant.MAX_RECORDS_PER_PAGE, Category.class));
		return "admin/add_book";
	}
	
	@RequestMapping("/add_category_request")
	public String addCategory() {
		return "admin/add_category";
	}
	
	@RequestMapping("/index")
	public String index() {
		return "admin/index";
	}
	
}
