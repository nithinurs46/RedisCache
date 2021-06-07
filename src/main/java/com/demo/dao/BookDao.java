package com.demo.dao;

import java.util.List;

import com.demo.model.Book;

public interface BookDao {

	public Book getBook(String bookName);

	public List<Book> getAllBooks();

	public List<Book> getAllBooksByPageNo(int pageNo);

	public Book addNewBook(Book book);

	public Book updateBook(Book book);

	public boolean deleteBook(String bookName);

}
