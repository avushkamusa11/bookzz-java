package online.library.services;

import java.util.Date;
import java.util.List;

import online.library.entities.Author;

public interface AuthorService {
	Author save(String name, Date dateOfBirth);
	Author get(long id);
	void update(long id, Date dateOfBirth, String name);
	List<Author> getAllAuthors();
	List<String> getAllAuthorsNames();
}
