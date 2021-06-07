package com.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import com.demo.dao.BookDao;
import com.demo.model.Book;

@Service
public class BookService {

	@Autowired
	BookDao dao;

	@Cacheable(value = "bookList")
	public List<Book> getAllBooks() {
		return dao.getAllBooks();
	}

	@Cacheable(value = "bookList", key = "#page")
	public List<Book> getAllBooksByPageNo(int page) {
		return dao.getAllBooksByPageNo(page);
	}

	@Cacheable(value = "book", key = "#bookName")
	public Book fetchBook(String bookName) throws Exception {
		return dao.getBook(bookName);
	}

	@Caching(evict = { @CacheEvict(value = "bookList", allEntries = true), }, put = {
			@CachePut(value = "book", key = "#book.getBookName()") })
	public Book addBook(Book book) {
		return dao.addNewBook(book);

	}

	@CachePut(value = "book", key = "#bookName")
	public Book updateBook(String bookName, Book book) {
		return dao.updateBook(book);

	}

	@Caching(evict = { @CacheEvict(value = "bookList", allEntries = true),
			@CacheEvict(value = "book", key = "#bookName"), })
	public boolean removeBook(String bookName) {
		return dao.deleteBook(bookName);
	}

}
