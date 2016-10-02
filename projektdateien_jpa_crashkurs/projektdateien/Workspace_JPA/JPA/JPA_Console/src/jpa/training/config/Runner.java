package jpa.training.config;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jpa.training.handlers.BooksHandler;
import jpa.training.models.Author;
import jpa.training.models.Book;

public class Runner {

	public static void main(String[] args) {
		
		AnnotationConfigApplicationContext ctx =
			new AnnotationConfigApplicationContext(AppConfig.class);
		
		ctx.scan("jpa.training");
		
		BooksHandler handler = ctx.getBean(BooksHandler.class);
		
		// Buch erstellen
		Book created = handler.createBook("Test", "123", 2010);
		handler.createBook("Test 2", "456", 2011);
		handler.createBook("Sonnenschein 123", "456", 2009);
		handler.createBook("Regen", "456", 1999);
		
		// Buch abrufen
		Book found = handler.getBook(1);
		System.out.println("Gefundenes Buch: " + found.getTitle());
		
		// Buch löschen
		handler.removeBook(found);
		System.out.println("Buch gelöscht!");
		
		// Bücher suchen
		List<Book> books = handler.find("nn");
		for(Book book : books) {
			System.out.println("==> " + book.getTitle());
		}				
		
		// Bücher nativ abrufen
		List<Book> nativeBooks = handler.getBooksNatively();
		for(Book book : nativeBooks) {
			System.out.println("==> " + book.getTitle());
		}
		
		// Anzahl der Bücher ausgeben
		System.out.println("==> Anzahl der Bücher: " + handler.getBooksCount());

		// Buch mit einem Autor anlegen
		handler.createBook("Kompendium #1", "456", 1999, "Karsten Samaschke");
		
		// Buch mit mehreren Autoren anlegen
		Book bookWithAuthors = handler.createBook("Kompendium #2", "456", 2005, "Karsten Samaschke", "Peter Meier", "Klaus Müller");
	
		// Autoren ausgeben
		for(Author author : bookWithAuthors.getData().getAuthors()) {
			System.out.println("Autor " + author.getId() + " - " + author.getName());
		}
		
		// Buch mit Autoren und Rating anlegen
		Book bookWithRating = handler.createBook(
			"Kompendium #3", "456", 2005,
			new Integer[] { 5, 5, 5, 5, 4, 6 },
			"Karsten Samaschke", "Peter Meier", "Klaus Müller");
	
		// Autoren ausgeben
		for(Author author : bookWithRating.getData().getAuthors()) {
			System.out.println("Autor " + author.getId() + " - " + author.getName());
		}
		
		// Ratings ausgeben
		for(int rating : bookWithRating.getData().getRatings()) {
			System.out.println("Wertung " + rating + " / 5");
		}
	
	}

}
