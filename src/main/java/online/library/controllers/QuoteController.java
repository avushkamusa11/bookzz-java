package online.library.controllers;

import java.util.List;

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
	@GetMapping("/all")
	public List<Quote> getAll(){
		return quoteService.getAll();
	}
	@GetMapping("/myQuotes")
	public List<Quote> getMyQuotes(){
		return quoteService.getByUsername();
	}
	
	@GetMapping("/byBook/{id}")
	public List<Quote> getQuotesByBook(@PathVariable(name = "id") String id){
		return quoteService.getByBook(Long.parseLong(id));
	}
	
	@PostMapping(value="/{bookId}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Quote saveQuote(@RequestBody String quoteText, @PathVariable(name = "bookId") String bookId) {
		System.out.println(quoteText);
		Quote quote = quoteService.save(quoteText, Long.parseLong(bookId));
		return quote;
	}
	
	@PutMapping("{id}/{bookId}")
	public Quote updateQuote(@PathVariable(name= "id") String id, @RequestBody String quoteText, @PathVariable(name = "bookId") String bookId) {
		Quote quote = quoteService.update(Integer.parseInt(bookId), quoteText, Long.parseLong(bookId));
		return quote;
	}
}
