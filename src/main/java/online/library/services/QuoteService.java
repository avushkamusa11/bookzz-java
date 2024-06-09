package online.library.services;

import java.util.List;

import online.library.entities.Quote;

public interface QuoteService {
	Quote save(String quote, long bookId);
	Quote update(long id, String quote, long bookId);
	Quote get(long id);
	List<Quote> getAll();
	List<Quote> getByBook(long bookId);
	List<Quote> getByUsername();
}
