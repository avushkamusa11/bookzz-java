package online.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.Book;
import online.library.entities.Genre;
import org.springframework.data.jpa.repository.Query;

public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {
	List<Book> findBookByGenre(Genre genre);

    Book findBookByTitle(String title);

    Book getBookByTitle(String title);

    Book findByTitle(String title);

   // @Query("SELECT c FROM Conversation c where sender_id=:sender and reciever_id = :reciever or sender_id =:reciever and reciever_id =:sender order by send_time asc")
}
