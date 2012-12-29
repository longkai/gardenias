package cn.longkai.gardenias.repository.impl;

import org.springframework.stereotype.Repository;

import cn.longkai.gardenias.entity.Book;
import cn.longkai.gardenias.repository.BookDao;

/**
 * 图书的数据访问实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Repository
public class BookDaoImpl extends GeneralDaoImpl<Book> implements BookDao {

}
