package online.library.services;

import java.util.List;

import online.library.beans.QuoteInputBean;
import online.library.entities.Quote;

public interface QuoteService {
	Quote save(QuoteInputBean input, String token);
	Quote update(long id, boolean isPrivate);
	Quote get(long id);
	List<Quote> getAll();
	List<Quote> getByBook(long bookId);
	List<Quote> getByUsername(String token);
}
