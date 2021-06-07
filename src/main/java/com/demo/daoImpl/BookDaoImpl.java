package com.demo.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.demo.dao.BookDao;
import com.demo.model.Book;

@Repository
public class BookDaoImpl implements BookDao {

	@Autowired
	private EntityManager entityManager;

	private Session getSession() {
		return entityManager.unwrap(Session.class);
	}

	@Override
	public Book getBook(String bookName) {
		Session session = getSession();
		System.out.println("Get Book From DB");
		Book book = session.get(Book.class, bookName);
		return book;
	}

	@Override
	public List<Book> getAllBooks() {
		Session session = getSession();
		System.out.println("Get All Books From DB");
		List<Book> booksList = session.createQuery("from BOOK").list(); // mention the entity name here
		return booksList;
	}
	
	@Override
	public List<Book> getAllBooksByPageNo(int pageNo) {
		Session session = getSession();
		System.out.println("Get All Books By Page No From DB");
		Query query = session.createQuery("from BOOK");// mention the entity name here
		int maxRecords = 10;
		query.setFirstResult((pageNo - 1) * maxRecords);
		query.setMaxResults(10);
		List<Book> booksList = query.list();
		return booksList;
	}

	@Override
	public Book addNewBook(Book book) {
		Session session = getSession();
		System.out.println("Add Book to DB");
		Transaction tx = session.beginTransaction();
		session.save(book);
		tx.commit();
		return book;
	}

	@Override
	public Book updateBook(Book book) {
		Session session = getSession();
		System.out.println("Update Book To DB");
		Transaction tx = session.beginTransaction();
		session.update(book);
		tx.commit();
		return book;
	}

	@Override
	public boolean deleteBook(String bookName) {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		System.out.println("Delete Book From DB");
		Book book = session.load(Book.class, bookName);
		if (book != null) {
			session.delete(book);
			tx.commit();
			return true;
		}
		return false;
	}

}
