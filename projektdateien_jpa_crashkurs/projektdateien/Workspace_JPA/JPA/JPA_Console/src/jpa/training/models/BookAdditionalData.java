package jpa.training.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Embeddable
public class BookAdditionalData {
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(
		name = "booksAndAuthors",
		joinColumns = {@JoinColumn(name = "book", referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "author", referencedColumnName = "id")})
	private List<Author> authors = new ArrayList<Author>();
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(
		name = "ratings",
		joinColumns = @JoinColumn(name = "book", referencedColumnName = "id"))
	private List<Integer> ratings = new ArrayList<Integer>();

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public List<Integer> getRatings() {
		return ratings;
	}

	public void setRatings(List<Integer> ratings) {
		this.ratings = ratings;
	}

}
