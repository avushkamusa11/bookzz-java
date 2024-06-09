package online.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import online.library.entities.Author;



public interface AuthorRepository extends JpaRepository<Author, Long>, JpaSpecificationExecutor<Author>{
	Author findByName(String name);
	@Query("SELECT a.name FROM Author a")
    List<String> findAuthorNames();

}
