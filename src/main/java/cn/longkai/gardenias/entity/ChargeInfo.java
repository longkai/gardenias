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
 * 读者罚款的历史记录
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Entity
@Table(name = "charge_info")
public class ChargeInfo {

	@Id
	@GeneratedValue
	private int id;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "reader_id")
	private Reader reader;
	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "book_id")
	private Book book;
	private float fee;
	@Column(name = "charge_date")
	private Date chargeDate;

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

	public float getFee() {
		return fee;
	}

	public void setFee(float fee) {
		this.fee = fee;
	}

	public Date getChargeDate() {
		return chargeDate;
	}

	public void setChargeDate(Date chargeDate) {
		this.chargeDate = chargeDate;
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
		ChargeInfo other = (ChargeInfo) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ChargeInfo [id=" + id + ", reader=" + reader + ", book=" + book
				+ ", fee=" + fee + ", chargeDate=" + chargeDate + "]";
	}
	
	/**
	 * 按照当前的系统时间获得一个罚款记录实例。
	 * @param book
	 * @param reader
	 * @param fee
	 */
	public static ChargeInfo getInstance(Book book, Reader reader, float fee) {
		ChargeInfo chargeInfo = new ChargeInfo();
		chargeInfo.setBook(book);
		chargeInfo.setReader(reader);
		chargeInfo.setFee(fee);
		chargeInfo.setChargeDate(new Date());
		return chargeInfo;
	}

}
