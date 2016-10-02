package jpa.training.handlers;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import jpa.training.models.Author;
import jpa.training.models.Book;
import jpa.training.models.BookDTO;

@Component
@Transactional
public class BooksHandler {

	@PersistenceContext
	private EntityManager em;
	
	public Book createBook(String title, String isbn, int year) {
		Book book = new Book();
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setYear(year);
		
		em.persist(book);
		System.out.println("ID ==> " + book.getId());
		
		return book;
	}
	
	public Book createBook(String title, String isbn, int year, String ... authors) {
		Book book = new Book();
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setYear(year);
		
		for(String author : authors) {
			book.getData().getAuthors().add(new Author(author));
		}
		
		em.persist(book);
		System.out.println("ID ==> " + book.getId());
		
		return book;
	}
	
	public Book createBook(String title, String isbn, int year, Integer[] ratings, String ... authors) {
		Book book = new Book();
		book.setTitle(title);
		book.setIsbn(isbn);
		book.setYear(year);
		
		for(String author : authors) {
			book.getData().getAuthors().add(new Author(author));
		}
		
		for(int rating : ratings) {
			book.getData().getRatings().add(rating);
		}
		
		em.persist(book);
		System.out.println("ID ==> " + book.getId());
		
		return book;
	}
	
	public Book getBook(int id) {
		
		// Sucht ein Buch
		return em.find(Book.class, id);
	}
	
	/**
	 * Löscht ein Buch
	 */
	public void removeBook(Book book) {
		// Mergen in den aktuellen EM-Kontext hinein
		book = em.merge(book);
		
		// Buch löschen
		em.remove(book);
	}
	
	/**
	 * Abfrage der Bücher nach dem Titel
	 */
	public List<Book> find(String title) {
		
		boolean hasTitle = title != null && title.length() > 0;
		String queryToUse = hasTitle ? Book.QUERY_FIND : Book.QUERY_FINDALL;
		
		// Abfrage definieren
		TypedQuery<Book> query = em.createNamedQuery(queryToUse, Book.class);
		
		// Parameter setzen
		if(hasTitle) {
			query.setParameter("title", "%" + title + "%");
		}
		
		// Ausführen
		return query.getResultList();				
	}	

	/**
	 * Gibt die Anzahl der Bücher zurück
	 */
	public int getBooksCount() {
		BigInteger count = (BigInteger) em.createNativeQuery("SELECT COUNT(1) FROM books").getSingleResult();
		
		return count.intValue();
	}
	
	/**
	 * Gibt die Liste der Bücher zurück
	 */
	public List<Book> getBooksNatively() {
		
		List<Book> result = em.createNativeQuery("SELECT * FROM books", Book.class).getResultList();
		
		return result;
	}
	
	/**
	 * Gibt eine Liste von BookDTO-Objekten zurück
	 */
	public List<BookDTO> getBooksAsDTO() {
		
		return em.createQuery(
				"SELECT new jpa.training.models.BookDTO(b.id, b.title) FROM Book b",
				BookDTO.class)
			.getResultList();
		
	}
}
