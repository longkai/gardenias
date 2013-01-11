package cn.longkai.gardenias.util;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

public class LibraryUtilTest {

	@Test(expected = RuntimeException.class)
	public void testCheckNull() {
		Object[] objects = null;
//		见方法代码处的注释
		LibraryUtil.checkNull(objects);
		LibraryUtil.checkNull(null, null);
	}

	@Test
	public void testRunout() {
		boolean b = LibraryUtil.runout(new Date(), -1);
		assertThat(b, is(false));
	}
	
	@Test
	public void testCalculateDaysGap() {
		Calendar c = Calendar.getInstance();
		c.set(2012, Calendar.NOVEMBER, 30);
		long t1 = LibraryUtil.calculateMillisGap(c.getTime());
		assertTrue(t1 > 0);
		long l = LibraryUtil.calculateDaysGap(c.getTime());
		assertThat(l, is(30L));
		long t2 = LibraryUtil.calculateDaysGap(new Date());
		assertThat(t2, is(0L));
	}

}
