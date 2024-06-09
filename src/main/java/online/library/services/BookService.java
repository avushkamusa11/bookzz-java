package online.library.services;

import java.util.List;

import online.library.entities.Author;
import online.library.entities.Book;
import online.library.entities.Genre;

public interface BookService {
	 public Book save(String title, String isbn, String description, int pages, String genreNames, List<Author> authorsNames,String file);
	 public Book update(long id, String title, String isbn, String description, int pages, String genreNames, List<Author> authorsNames,String file);
	 public Book update(long id, String fileName);
	 public Book updatePdf(long id, String pdf);
	 public Book get(long id);
	 public Book findByTitle(String title);
	 public List<Book> getAllBooks();
	 public void delete(long id);
	 public Book rateBook(long id, int grade);
	 public List<Book> getBooksByGenre();
	 
	 

}
