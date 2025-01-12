package online.library.controllers;

import java.io.IOException;
import java.util.List;

import online.library.beans.MyBookBean;
import online.library.helpers.UserBookHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.UserBook;
import online.library.services.UserBookService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/myLibrary")
public class UserBookController {
	@Autowired
	private UserBookService userBookservice;

	@Autowired
	private UserBookHelper helper;
	
	@PostMapping("/{bookId}/{status}/{token}")
	public Long save(@PathVariable(name = "bookId") String bookId, @PathVariable(name = "status") String status, @PathVariable String token) {
		UserBook userBook = userBookservice.save(Long.parseLong(bookId), status,token);
		return userBook.getId();
	}
	
	@PutMapping("/{bookId}/{status}/{token}")
	public MyBookBean update(@PathVariable(name = "bookId") String bookId, @PathVariable(name = "status") String status, @PathVariable(name = "token") String token) throws IOException {
		UserBook userBook = userBookservice.update(Long.parseLong(bookId), status,token);
		return helper.convertToMyBookBean(userBook);
	}
	
	@GetMapping("/myBooks/{token}")
	public List<MyBookBean> getMyBooks(@PathVariable String token) throws IOException {
		List<UserBook> usersBooks = userBookservice.getByToken(token);
		return helper.convertToMyBookList(usersBooks);
	}
	@GetMapping("/books/{userId}")
	public List<UserBook> getBooksByUser(@PathVariable(name = "userId") String userId){
		return userBookservice.getByUser(Long.parseLong(userId));
	}
	
	@GetMapping("/checkBook/{bookId}")
	public boolean checkBook(@PathVariable(name = "bookId") String bookId ) {
		return userBookservice.isInMyLibrary(Long.parseLong(bookId));
	}
	
	@GetMapping("/getCount/{id}")
	public int getCount(@PathVariable(name = "id") String id) {
		return userBookservice.countOfBooks(Long.parseLong(id));
	}
	@GetMapping("/get/{bookId}")
	public UserBook getByBookId(@PathVariable(name = "bookId") String bookId) {
		return userBookservice.getByBookId(Long.parseLong(bookId));
	}
	@GetMapping("/readBooks/{year}/{token}")
	public List<MyBookBean> getReadBooks(@PathVariable(name = "year") String year, @PathVariable String token) throws IOException {
		List<UserBook> books =  userBookservice.findByUserAndStatus(Integer.parseInt(year), token);
		return  helper.convertToMyBookList(books);
	}
}
