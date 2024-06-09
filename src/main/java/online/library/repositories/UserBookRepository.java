package online.library.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import online.library.entities.Author;
import online.library.entities.Book;
import online.library.entities.User;
import online.library.entities.UserBook;

public interface UserBookRepository  extends JpaRepository<UserBook, Long>, JpaSpecificationExecutor<UserBook> {
	UserBook findByUserUsernameAndBookId(String username, long id);
	List<UserBook> findUserBookByUserUsername(String username);
	List<UserBook> findUserBookByUser(User user);
	@Query("SELECT count(ub.id) FROM UserBook ub where user=:user")
    int findCountOfBooks(User user);
	UserBook findByBookAndUser(Book book,User user);
	List<UserBook> findByUserAndStatus(User user, String status);
	
}
