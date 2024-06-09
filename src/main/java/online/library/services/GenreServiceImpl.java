package online.library.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Genre;
import online.library.repositories.GenreRepository;

@Service
public class GenreServiceImpl implements GenreService {
	@Autowired
	GenreRepository genreRepository;
	
	Genre genre;

	@Override
	public Genre save(String genreName) {
		genre = new Genre();
		genre.setGenreName(genreName);
		genreRepository.save(genre);
		return genre;
	}

	@Override
	public Genre get(long id) {
		genre = genreRepository.findById(id).get();
		return genre;
	}

	@Override
	public Genre update(long id, String genreName) {
		genre = get(id);
		genre.setGenreName(genreName);
		genreRepository.save(genre);
		return genre;

	}

	@Override
	public List<Genre> getAllGenre() {
		List<Genre> genres = genreRepository.findAll();
		return genres;
	}

}
