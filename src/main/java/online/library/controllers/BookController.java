package online.library.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.validation.Valid;

import online.library.beans.BookBean;
import online.library.beans.BookOutputBean;
import online.library.helpers.BookHelper;
import online.library.helpers.FileConverterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import online.library.beans.BookInput;
import online.library.entities.Author;
import online.library.entities.Book;
import online.library.entities.Genre;
import online.library.entities.UserBook;
import online.library.services.BookService;
import online.library.services.FileService;
import online.library.services.UserBookService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/book")
public class BookController {
	@Autowired
	private BookService bookService;

	@Autowired
	private BookHelper bookHelper;

	@Autowired
	private UserBookService userBookService;
	
	@Autowired
	private FileService fileService;
	
	@GetMapping("/{id}")
	public BookOutputBean getBook(@PathVariable(name = "id") String id) throws MalformedURLException {
		Book book = bookService.get(Long.parseLong(id));
		Long readers = bookService.getReaders(Long.parseLong(id));
		 BookOutputBean bean = bookHelper.convertToBookOutputBean(book);
		 bean.setReaders(readers);
		 return bean;
	}

	@GetMapping("/pdf/{id}")
	public String getBookPdf(@PathVariable(name = "id") String id) throws IOException {
		Book book = bookService.get(Long.parseLong(id));
		String url = book.getBookPdf();
		return FileConverterHelper.getPdf(url);
	}

//	@GetMapping("/pdf/{id}")
//	public String getBookPdf(@PathVariable(name = "id") String id) throws MalformedURLException {
//		Book book = bookService.get(Long.parseLong(id));
//		return book.getBookPdf();
//	}


	
//	@PostMapping("/save/{title}/{isbn}/{pages}/{description}/{genres}")
//	public Book saveBook(@PathVariable(name = "title") String title,@PathVariable(name = "isbn") String isbn,
//			@PathVariable(name = "pages") String pages,@PathVariable(name = "description") String description,@PathVariable(name = "genres") String genre,
//			@RequestBody List<Author> authors){
	@PostMapping("/save")
	public Book saveBook(@RequestBody BookInput book){
		String file = fileService.getImageForStoreInDB();
		
		 String title = book.getTitle();
		 String isbn = book.getIsbn();
		 String pages = book.getPages();
		 String description = book.getDescription();
		 String genre = book.getGenre();
		 List<Author> authors = book.getAuthors();
		
		return bookService.save(title, isbn, description, Integer.parseInt(pages), genre, authors, file);
		
	}
	@PutMapping("/update/{id}")
	public Book updateBook(@PathVariable(name = "id") String id, @RequestBody BookInput book){
		String file = fileService.getImageForStoreInDB();
		String title = book.getTitle();
		 String isbn = book.getIsbn();
		 String pages = book.getPages();
		 String description = book.getDescription();
		 String genre = book.getGenre();
		 List<Author> authors = book.getAuthors();
		return bookService.update(Long.parseLong(id), title, isbn, description, Integer.parseInt(pages), genre, authors,file);
		
	}
	@GetMapping("/all/{token}")
	public ResponseEntity<List<BookBean>> getAllBooks(@PathVariable String token){
		List<UserBook> usersBooks = userBookService.getByToken(token);
		List<Book> books = bookService.getAllBooks();
			for(UserBook ub: usersBooks) {
				books.removeIf(book -> book.getId().equals(ub.getBook().getId()));
			}
		
		return ResponseEntity.ok(bookHelper.convertToList(books));
	}
	
	@DeleteMapping("/{id}")
	public void deleteBook(@PathVariable (name ="id") String id) {
		bookService.delete(Long.parseLong(id));
	}
	
	@PutMapping("rate/{id}/{grade}")
	public Book rateBook(@PathVariable(name = "id") String id, @PathVariable(name = "grade") String grade) {
		return bookService.rateBook(Long.parseLong(id), Integer.parseInt(grade));
	}
	@GetMapping("/fantasy")
	public List<Book> fantasyBooks(){
		return bookService.getBooksByGenre();
	}
}
