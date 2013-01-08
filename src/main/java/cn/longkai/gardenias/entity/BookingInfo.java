package cn.longkai.gardenias.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 读者预约的历史记录
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Entity
@Table(name = "booking_info")
public class BookingInfo {

	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "reader_id")
	private Reader reader;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	private Book book;
	@Column(name = "book_date")
	private Date date;
	private boolean cancel;
	private boolean deal;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reader getReader() {
		return reader;
	}

	public void setReader(Reader reader) {
		this.reader = reader;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public boolean isDeal() {
		return deal;
	}

	public void setDeal(boolean deal) {
		this.deal = deal;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookingInfo other = (BookingInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookingInfo [id=" + id + ", reader=" + reader + ", book="
				+ book + ", date=" + date + ", cancel=" + cancel
				+ ", deal=" + deal + "]";
	}
	
	/**
	 * 获得一个图书预约实例，预约时间为当前系统时间。
	 * @param book
	 * @param reader
	 * @return 实例化后的对象
	 */
	public static BookingInfo getInstance(Book book, Reader reader) {
		BookingInfo bookingInfo = new BookingInfo();
		bookingInfo.setBook(book);
		bookingInfo.setReader(reader);
//		对象一初始化就是false，没有必要设置了
//		bookingInfo.setDeal(false);
//		bookingInfo.setCancel(false);
		bookingInfo.setDate(new Date());
		return bookingInfo;
	}
	
}
