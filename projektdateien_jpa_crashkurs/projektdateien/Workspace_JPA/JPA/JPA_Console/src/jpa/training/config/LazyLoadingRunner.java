package jpa.training.config;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jpa.training.handlers.BooksHandler;
import jpa.training.models.Author;
import jpa.training.models.Book;

public class LazyLoadingRunner {

	public static void main(String[] args) {
		
		// Initialisieren der Konfiguration
		AnnotationConfigApplicationContext ctx =
			new AnnotationConfigApplicationContext(AppConfig.class);
		
		ctx.scan("jpa.training");
		
		// handler abrufen
		BooksHandler handler = ctx.getBean(BooksHandler.class);
		
		// Buch mit Autoren und Rating anlegen
		Book bookWithRating = handler.createBook(
			"Kompendium #3", "456", 2005,
			new Integer[] { 5, 5, 5, 5, 4, 6 },
			"Karsten Samaschke", "Peter Meier", "Klaus Müller");
		
		// Daten anzeigen
		showData(bookWithRating);
		
		List<Book> found = handler.find("#3");
		for(Book book : found) {
			showData(book);
		}
	}

	/**
	 * Gibt die Daten eines Buches aus
	 */
	private static void showData(Book book) {
		
		// Autoren ausgeben
		for(Author author : book.getData().getAuthors()) {
			System.out.println("Autor " + author.getId() + " - " + author.getName());
		}
		
		// Ratings ausgeben
		for(int rating : book.getData().getRatings()) {
			System.out.println("Wertung " + rating + " / 5");
		}
	}
	
}
