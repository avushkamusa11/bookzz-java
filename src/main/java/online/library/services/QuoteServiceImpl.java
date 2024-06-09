package online.library.services;

import java.util.List;

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
	
	@Override
	public Quote save(String quote, long bookId) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		Book book = bookRepository.findById(bookId).get();
		Quote quoteObj = new Quote();
		quoteObj.setBook(book);
		quoteObj.setQuote(quote);
		quoteObj.setUser(user);
		quoteRepository.save(quoteObj);
		return quoteObj;

	}

	@Override
	public Quote update(long id, String quote, long bookId) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		Book book = bookRepository.findById(bookId).get();
		Quote quoteObj = quoteRepository.findById(id).get();
		quoteObj.setBook(book);
		quoteObj.setQuote(quote);
		quoteObj.setUser(user);
		quoteRepository.save(quoteObj);
		return quoteObj;

	}

	@Override
	public Quote get(long id) {
		return quoteRepository.findById(id).get();
	}

	@Override
	public List<Quote> getAll() {
		return quoteRepository.findAll();
	}

	@Override
	public List<Quote> getByBook(long bookId) {
		return quoteRepository.findByBookId(bookId);
	}
	@Override
	public List<Quote> getByUsername() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		return quoteRepository.findByUserUsername(username);
	}

}
