package online.library.beans;
import java.util.List;

import javax.validation.constraints.NotNull;

import online.library.entities.Author;

public class BookInput {

	@NotNull(message = "Title cannot be null")
	private String title;
	private String isbn;
	private String pages;
	private String description;
	private String genre;
	private List<Author> authors;

	
	@NotNull(message = "Title cannot be null")
	public String getTitle() {
		return title;
	}
	@NotNull(message = "Title cannot be null")
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getGenre() {
		return genre;
	}
	public void setGenre(String genre) {
		this.genre = genre;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
}
