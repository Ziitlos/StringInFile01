package jpa.training.web;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jpa.training.handlers.BooksHandler;
import jpa.training.models.Book;

@Named
@RequestScoped
public class BooksQueryModel {

	private String title;
	
	private List<Book> books;
	
	@Inject
	private BooksHandler booksHandler;
	
	/**
	 * Führt die Abfrage aus
	 */
	public void query() {
		books = booksHandler.find(getTitle());
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Book> getBooks() {
		return books;
	}

	public void setBooks(List<Book> books) {
		this.books = books;
	}

}
