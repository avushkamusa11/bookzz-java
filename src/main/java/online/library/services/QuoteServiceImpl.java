package online.library.services;

import java.util.List;

import online.library.beans.QuoteInputBean;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Book;
import online.library.entities.Comment;
import online.library.entities.Quote;
import online.library.entities.User;
import online.library.repositories.BookRepository;
import online.library.repositories.QuoteRepository;
import online.library.repositories.UserRepository;

@Service
public class QuoteServiceImpl implements QuoteService {

	@Autowired
	QuoteRepository quoteRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;

	@Autowired
	UserService userService;
	
	@Override
	public Quote save(QuoteInputBean input, String token) {
		User user = userService.getUserByToken(token);
		Book book = bookRepository.findBookByTitle(input.getBookTitle());
		Quote quoteObj = new Quote();
		quoteObj.setBook(book);
		quoteObj.setQuote(input.getQuoteText());
		quoteObj.setUser(user);
		quoteObj.setPrivate(input.isPrivate());
		quoteRepository.save(quoteObj);
		return quoteObj;

	}

	@Override
	public Quote update(long id, boolean isPrivate) {
		Quote quoteObj = quoteRepository.findById(id).get();
		quoteObj.setPrivate(isPrivate);
		quoteRepository.save(quoteObj);
		return quoteObj;

	}

	@Override
	public Quote get(long id) {
		return quoteRepository.findById(id).get();
	}

	@Override
	public List<Quote> getAll() {
		return quoteRepository.findByIsPrivateTrue();
	}

	@Override
	public List<Quote> getByBook(long bookId) {
		return quoteRepository.findByBookId(bookId);
	}
	@Override
	public List<Quote> getByUsername(String token) {
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.getUserByToken(token);
		return quoteRepository.findByUserUsername(user.getUsername());
	}

}
