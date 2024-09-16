package online.library.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import online.library.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Author;
import online.library.entities.Book;
import online.library.entities.Genre;

@Service
public class BookServiceImpl implements BookService{
	@Autowired
	private BookRepository bookRepository;
	@Autowired
	private AuthorRepository authorRepository;
	@Autowired 
	private GenreRepository genreeRepository;
	@Autowired
	private BookDao bookDao;
	
	private Book book;
	
	@Override
	public Book save(String title, String isbn, String description, int pages, String genreNames, List<Author> authorsNames, String file){
		book = new Book();
		List<Author> authors = new ArrayList<Author>();
//		for(Object obj : authorsNames) {
//			
//			Author author = authorRepository.findByName((obj).toString());
//			authors.add(author);
//		}
//		for(Genre name : genreNames) {
//			genres.add(name);
//		}
		Genre genre = genreeRepository.findById(Long.parseLong(genreNames)).get();

		book.setTitle(title);
		book.setIsbn(isbn);
		book.setPages(pages);
		book.setDescription(description);
		book.setAuthors(authorsNames);
		book.setGenre(genre);
		book.setFile(file);
		bookRepository.save(book);
		return book;
	}

	@Override
	public Book update(long id, String title, String isbn, String description, int pages, String genreNames, List<Author> authorsNames,String file) {
		bookRepository.findById(id);

		Genre genre = genreeRepository.findById(Long.parseLong(genreNames)).get();

		book.setTitle(title);
		book.setIsbn(isbn);
		book.setPages(pages);
		book.setDescription(description);
		book.setAuthors(authorsNames);
		book.setGenre(genre);
		book.setFile(file);
		bookRepository.save(book);
		return book;
		
	}

	@Override
	public Book get(long id) {
		book = bookRepository.findById(id).get();
		return book;
	}

	@Override
	public Book findByTitle(String title) {
		return book;
	}

	@Override
	public List<Book> getAllBooks() {
		List<Book> books = bookRepository.findAll();
		return books;
	}

	@Override
	public void delete(long id) {
		bookRepository.deleteById(id);
		
	}

	@Override
	public Book update(long id, String fileName) {
		Book book = bookRepository.findById(id).get();
		book.setFile(fileName);
		bookRepository.save(book);
		return book;
	}

	@Override
	public Book rateBook(long id, int grade) {
		Book book = bookRepository.findById(id).get();
		double rate = book.getRate();
		if(rate != 0.0) {
			rate = (rate + grade) / 2;
		}else {
			rate = grade;
		}
		
		book.setRate(rate);
		bookRepository.save(book);
		return book;
	}

	@Override
	public Book updatePdf(long id, String pdf) {
		Book book = bookRepository.findById(id).get();
		book.setBookPdf(pdf);
		bookRepository.save(book);
		return book;
	}

	@Override
	public List<Book> getBooksByGenre() {
		Genre genre = genreeRepository.findByGenreName("Fantasy");
		
		List<Book> books = bookRepository.findBookByGenre(genre);
		return books;
	}

	@Override
	public Long getReaders(long id) {
		return bookDao.getCountOfReaders(id);
	}

}
