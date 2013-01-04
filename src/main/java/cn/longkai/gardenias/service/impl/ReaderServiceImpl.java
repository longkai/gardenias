package cn.longkai.gardenias.service.impl;

import java.util.Date;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.longkai.gardenias.entity.Reader;
import cn.longkai.gardenias.repository.ReaderDao;
import cn.longkai.gardenias.service.ReaderService;
import cn.longkai.gardenias.util.LibraryException;
import cn.longkai.gardenias.util.LibraryMessages;

/**
 * 读者对外服务接口的实现。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class ReaderServiceImpl implements ReaderService {

	private static final Logger l = LoggerFactory.getLogger(ReaderServiceImpl.class);
	
	@Inject
	private ReaderDao readerDao;
	
	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void register(Reader reader) {
		if (readerDao.exists(reader.getAccount())) {
			l.error("账号:{} 已经被注册过了", reader.getAccount());
			throw new LibraryException(LibraryMessages.ACCOUNT_HAS_BEEN_REGISTERED);
		}
		reader.setRegisterTime(new Date());
		readerDao.persist(reader);
		l.info("新读者注册成功！id:{}，账号：{}", reader.getId(), reader.getAccount());
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public Reader login(String account, String password) {
		Reader reader = null;
		try {
			reader = readerDao.find(account, password);
		} catch (Exception e) {
			l.error("登录失败！账号：" + account, e);
			throw new LibraryException(LibraryMessages.LOGIN_FAIL);
		}
		return reader;
	}

}
