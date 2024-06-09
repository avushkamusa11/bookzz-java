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

import online.library.entities.Genre;
import online.library.services.GenreService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/genre")
public class GenreController {
	@Autowired
	GenreService genreService;
	
	@GetMapping("/{id}")
	public Genre getGenre(@PathVariable(name = "id")String id) {
		Genre genre = genreService.get(Long.parseLong(id));
		return genre;
	}
	
	@PostMapping("/{genreName}")
	public Genre saveGenre(@PathVariable(name = "genreName")String genreName) {
		return genreService.save(genreName);
	}
	
	@PutMapping("/{id}/{genreName}")
	public void updateGenre(@PathVariable(name = "genreName") String genreName, @PathVariable (name = "id") String id) {
		
	}
	
	@GetMapping("/all")
	public List<Genre> getAll(){
		return genreService.getAllGenre();
	}

}
