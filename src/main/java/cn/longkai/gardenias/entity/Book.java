package cn.longkai.gardenias.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 书籍的实体类。
 * 
 * @author longkai
 * @since 2012-12-29
 */
@Entity
@Table(name = "book")
public class Book {

	@Id
	@GeneratedValue
	private int id;
	private String title;
	private String author;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "category_id")
	private Category category;
	@Column(name = "publish_house")
	private String publishHouse;
	@Column(name = "publish_date")
	private Date publishDate;
	private String ISBN;
	private float price;
	@Column(name = "borrowed_times")
	private int borrowedTimes;
	@Column(name = "booked_times")
	private int bookedTimes;
	@Column(name = "click_times")
	private int clickTimes;
	private int total;
	private int remain;
	@Column(name = "include_date")
	private Date includedDate;
	private String image;
	private String url;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getPublishHouse() {
		return publishHouse;
	}

	public void setPublishHouse(String publishHouse) {
		this.publishHouse = publishHouse;
	}

	public Date getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(Date publishDate) {
		this.publishDate = publishDate;
	}

	public String getISBN() {
		return ISBN;
	}

	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getBorrowedTimes() {
		return borrowedTimes;
	}

	public void setBorrowedTimes(int borrowedTimes) {
		this.borrowedTimes = borrowedTimes;
	}

	public int getBookedTimes() {
		return bookedTimes;
	}

	public void setBookedTimes(int bookedTimes) {
		this.bookedTimes = bookedTimes;
	}

	public int getClickTimes() {
		return clickTimes;
	}

	public void setClickTimes(int clickTimes) {
		this.clickTimes = clickTimes;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getRemain() {
		return remain;
	}

	public void setRemain(int remain) {
		this.remain = remain;
	}

	public Date getIncludedDate() {
		return includedDate;
	}

	public void setIncludedDate(Date includedDate) {
		this.includedDate = includedDate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
		Book other = (Book) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author
				+ ", category=" + category + ", publishHouse=" + publishHouse
				+ ", publishDate=" + publishDate + ", ISBN=" + ISBN
				+ ", price=" + price + ", borrowedTimes=" + borrowedTimes
				+ ", bookedTimes=" + bookedTimes + ", clickTimes=" + clickTimes
				+ ", total=" + total + ", remain=" + remain + ", includedDate="
				+ includedDate + ", image=" + image + ", url=" + url + "]";
	}

}
