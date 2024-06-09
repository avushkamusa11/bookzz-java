package online.library.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;
import org.springframework.stereotype.Service;

import online.library.entities.Author;
import online.library.entities.User;
import online.library.repositories.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
	@Autowired
	AuthorRepository authorRepository;

	@Override
	public Author save(String name, Date dateOfBirth) {
		Author author = new Author();
		author.setfName(name);
		author.setDateOfBirth(dateOfBirth);
		authorRepository.save(author);
		return author;
	}

	@Override
	public Author get(long id) {
		Author author = authorRepository.findById(id).get();
		return author;
	}

	@Override
	public void update(long id,Date dateOfBirth, String name) {
		Author author = get(id);
		author.setDateOfBirth(dateOfBirth);
		author.setfName(name);
	}

	@Override
	public List<Author> getAllAuthors() {
		List<Author> authors = authorRepository.findAll();
		return authors;
	}

	@Override
	public List<String> getAllAuthorsNames() {
		List<String> authorsNames = authorRepository.findAuthorNames();
		return authorsNames;
	}

}
