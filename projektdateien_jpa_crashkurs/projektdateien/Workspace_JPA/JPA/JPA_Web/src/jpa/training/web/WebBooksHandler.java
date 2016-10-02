package jpa.training.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import jpa.training.handlers.BooksHandler;
import jpa.training.models.Book;

@Named(value = "booksHandler")
@RequestScoped
public class WebBooksHandler {

	@Inject
	private BooksHandler booksHandler;
	
	@Inject
	private CurrentBook currentBook;
	
	private boolean bookCreated = false;
	
	public void createBook() {
		Book book = booksHandler.createBook(
				currentBook.getTitle(), 
				currentBook.getIsbn(), 
				currentBook.getYear());
		
		// Frontend-Magie :-)
		currentBook.initialize();
		currentBook.setId(book.getId());
		bookCreated = true;
		
		System.out.println("==> Buch mit ID " + book.getId() + " wurde angelegt");
	}

	public boolean isBookCreated() {
		return bookCreated;
	}

	public void setBookCreated(boolean bookCreated) {
		this.bookCreated = bookCreated;
	}
	
}
