package online.library.controllers;

import java.util.List;

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
	UserBookService userBookservice;
	
	@PostMapping("/{bookId}/{status}")
	public UserBook save(@PathVariable(name = "bookId") String bookId, @PathVariable(name = "status") String status) {
		UserBook userBook = userBookservice.save(Long.parseLong(bookId), status);
		return userBook;
	}
	
	@PutMapping("/{bookId}/{status}")
	public UserBook update(@PathVariable(name = "bookId") String bookId, @PathVariable(name = "status") String status) {
		UserBook userBook = userBookservice.update(Long.parseLong(bookId), status);
		return userBook;
	}
	
	@GetMapping("/myBooks")
	public List<UserBook> getMyBooks(){
		List<UserBook> usersBooks = userBookservice.getByUserUsername();
		return usersBooks;
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
	@GetMapping("/readBooks/{year}")
	public List<UserBook> getReadBooks(@PathVariable(name = "year") String year){
		return userBookservice.findByUserAndStatus(Integer.parseInt(year));
	}
}
