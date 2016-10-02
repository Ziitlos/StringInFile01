package jpa.training.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "books")
@NamedQueries({
	@NamedQuery(name=Book.QUERY_FINDALL, query="SELECT b FROM Book b"),
	@NamedQuery(name=Book.QUERY_FIND, query="SELECT b FROM Book b WHERE b.title LIKE :title")
})
public class Book {

	public static final String QUERY_FINDALL = "Book.FindAll";
	public static final String QUERY_FIND = "Book.Find";
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String title;
	
	private String isbn;
	
	private int year;
	
	@Embedded
	private BookAdditionalData data = new BookAdditionalData();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public BookAdditionalData getData() {
		return data;
	}

	public void setData(BookAdditionalData data) {
		this.data = data;
	}
}
