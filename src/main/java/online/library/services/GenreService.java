package online.library.services;

import java.util.List;

import online.library.entities.Genre;

public interface GenreService {
	public Genre save(String genreName);
	public Genre get(long id);
	public Genre update(long id, String genreName);
	public List<Genre> getAllGenre();

}
