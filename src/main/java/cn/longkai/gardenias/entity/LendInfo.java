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
 * 读者的借书信息历史记录。
 * 
 * @author longkai
 * @since 2012-1229
 * 
 */
@Entity
@Table(name = "lend_info")
public class LendInfo {

	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "reader_id")
	private Reader reader;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "book_id")
	private Book book;
	@Column(name = "lend_date")
	private Date date;
	@Column(name = "return_date")
	private Date returnDate;
	private boolean charge;

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

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public boolean isCharge() {
		return charge;
	}

	public void setCharge(boolean charge) {
		this.charge = charge;
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
		LendInfo other = (LendInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "LendInfo [id=" + id + ", reader=" + reader + ", book=" + book
				+ ", date=" + date + ", returnDate=" + returnDate
				+ ", charge=" + charge + "]";
	}
	
	/**
	 * 获取一个以当前系统时间为借书时间的借书信息实例。
	 * @param book
	 * @param reader
	 */
	public static LendInfo getInstance(Book book, Reader reader) {
		LendInfo lendInfo = new LendInfo();
//		默认的就不写了0=0
		lendInfo.setBook(book);
		lendInfo.setDate(new Date());
		lendInfo.setReader(reader);
		return lendInfo;
	}
	
}
