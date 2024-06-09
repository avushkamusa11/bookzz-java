package online.library.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.Author;
import online.library.services.AuthorService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/author")
public class AuthorController {
	@Autowired
	AuthorService authorService;
	
	@GetMapping("/{id}")
	public Author getAuthor(@PathVariable(name = "id") String  id) {
		Author author = authorService.get(Long.parseLong(id));
		return author;
		}
	@PostMapping("/{name}/{dateOfBirth}")
	public Author saveAuthor(@PathVariable(name = "name") String name, @PathVariable(name="dateOfBirth") Date dateOfBirht) throws ParseException {
		//Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirht);
		return authorService.save(name, dateOfBirht);
	}
	
	@PutMapping("/{id}/{name}/{dateOfBirth}")
	public void updateAuthor(@PathVariable(name="id") String id, @PathVariable(name = "name") String name, @PathVariable(name="dateOfBirth") String dateOfBirht) throws ParseException {
		Date date=new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirht);
		authorService.update(Long.parseLong(id), date, name);
	}
	@GetMapping("/all")
	public List<Author> getAllAuthors(){
		List<Author> authors = authorService.getAllAuthors();
		return authors;
	}
	
	@GetMapping("/allNames") 
	public List<String> getAllAuthorsNames(){
		return authorService.getAllAuthorsNames();
	}
	

}
