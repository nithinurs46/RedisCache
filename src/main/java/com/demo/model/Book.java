package com.demo.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name="BOOK")
@Table(name = "BOOK")
public class Book implements Serializable {
	
	private static final long serialVersionUID = 1790051662087188625L;
	@Id
	@Column(name = "BOOK_NAME")
	private String bookName;
	@Column(name = "AUTHOR")
	private String author;
	@Column(name = "PUBLISHER_NAME")
	private String publisherName;
	
	public Book() {
	}
	
	
	public Book(String bookName, String author, String publisherName) {
		this.bookName = bookName;
		this.author = author;
		this.publisherName = publisherName;
	}
	
	public String getBookName() {
		return bookName;
	}
	public void setBookName(String bookName) {
		this.bookName = bookName;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getPublisherName() {
		return publisherName;
	}
	public void setPublisherName(String publisherName) {
		this.publisherName = publisherName;
	}

}
