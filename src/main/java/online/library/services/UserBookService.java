package online.library.services;

import java.util.List;

import online.library.entities.UserBook;

public interface UserBookService {
	UserBook save(long bookId, String status);
	UserBook update(long bookId, String status);
	List<UserBook> getByUserUsername();
	List<UserBook> getByUser(long id);
	boolean isInMyLibrary(long id);
	int countOfBooks(long id);
	UserBook getByBookId(long id);
	List<UserBook> findByUserAndStatus(int year);
}
