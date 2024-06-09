package online.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.Genre;

public interface GenreRepository  extends JpaRepository<Genre, Long>, JpaSpecificationExecutor<Genre> {
	Genre findByGenreName(String genreName);

}
