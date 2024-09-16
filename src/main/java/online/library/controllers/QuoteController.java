package online.library.controllers;

import java.io.IOException;
import java.util.List;

import online.library.beans.QuoteInputBean;
import online.library.beans.QuoteOutputBean;
import online.library.helpers.QuoteHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import online.library.entities.Quote;
import online.library.services.QuoteService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/quotes")
public class QuoteController {

	@Autowired
	private QuoteService quoteService;

	@Autowired
	private QuoteHelper helper;
	@GetMapping("/all")
	public List<QuoteOutputBean> getAll() throws IOException {
		return helper.convertToList(quoteService.getAll());
	}
	@GetMapping("/myQuotes/{token}")
	public List<QuoteOutputBean> getMyQuotes(@PathVariable String token) throws IOException {
		return helper.convertToList(quoteService.getByUsername(token));
	}
	
	@GetMapping("/byBook/{id}")
	public List<Quote> getQuotesByBook(@PathVariable(name = "id") String id){
		return quoteService.getByBook(Long.parseLong(id));
	}
	
//	@PostMapping(value="/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE)
//	public Quote saveQuote(@RequestBody String quoteText, @PathVariable(name = "bookId") String bookId) {
//		System.out.println(quoteText);
//		Quote quote = quoteService.save(quoteText, Long.parseLong(bookId));
//		return quote;
//	}
	@PostMapping(value="/add/{token}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public QuoteOutputBean saveQuote(@RequestBody QuoteInputBean input, @PathVariable String token) throws IOException {
		Quote quote = quoteService.save(input, token);
		return helper.convertToBean(quote);
	}
	
	@PutMapping("{id}/{isPrivate}")
	public QuoteOutputBean updateQuote(@PathVariable(name= "id") String id, @PathVariable(name = "isPrivate") boolean isPrivate) throws IOException {
		Quote quote = quoteService.update(Integer.parseInt(id), isPrivate);
		return helper.convertToBean(quote);
	}
}
