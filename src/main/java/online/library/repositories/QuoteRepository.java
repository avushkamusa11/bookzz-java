package online.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import online.library.entities.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>, JpaSpecificationExecutor<Quote> {
	List<Quote> findByUserUsername(String username);
	List<Quote> findByBookId(long bookId);
}
