package jpa.training.config;

import java.util.List;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import jpa.training.handlers.BooksHandler;
import jpa.training.models.Book;
import jpa.training.models.BookDTO;

public class DTORunner {

	public static void main(String[] args) {
		// Initialisieren der Konfiguration
		AnnotationConfigApplicationContext ctx =
			new AnnotationConfigApplicationContext(AppConfig.class);
		
		ctx.scan("jpa.training");
		
		// handler abrufen
		BooksHandler handler = ctx.getBean(BooksHandler.class);
		
		// Bücher anlegen
		handler.createBook("Test", "123", 2010);
		handler.createBook("Test 2", "456", 2011);
		handler.createBook("Sonnenschein 123", "456", 2009);
		handler.createBook("Regen", "456", 1999);
		
		handler.createBook("Kompendium #3", "456", 2005,
			new Integer[] { 5, 5, 5, 5, 4, 6 },
			"Karsten Samaschke", "Peter Meier", "Klaus Müller");
		
		// Bücher abrufen
		List<BookDTO> books = handler.getBooksAsDTO();
		
		for(BookDTO book : books) {
			System.out.println(book.getId() + ": " + book.getTitle());
		}
	}

}
