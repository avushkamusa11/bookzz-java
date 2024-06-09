package online.library.services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.catalina.startup.ClassLoaderFactory.Repository;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Book;
import online.library.entities.Statistic;
import online.library.entities.User;
import online.library.entities.UserBook;
import online.library.repositories.BookRepository;
import online.library.repositories.StatisticRepository;
import online.library.repositories.UserBookRepository;
import online.library.repositories.UserRepository;

@Service
public class UserBookServiceImpl implements UserBookService {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserBookRepository userBookRepository;
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	StatisticRepository statisticRepository;
	
	@Override
	public UserBook save(long bookId, String status) {
		String loggedUsersUsername = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(loggedUsersUsername);
		Book book = bookRepository.findById(bookId).get();
		
		UserBook userBook = new UserBook();
		userBook.setBook(book);
		userBook.setStatus(status);
		userBook.setUser(user);
		
		if(status.equals("Read")){
			userBook.setDateOfRead(LocalDate.now());
			Statistic checkStatistic = statisticRepository.findByUserUsername(loggedUsersUsername);
			Statistic statistic;
			if(checkStatistic.equals(null)) {
				statistic = new Statistic();
			}else {
				statistic = checkStatistic;
			}
			int countOfPages = statistic.getCountOfPages();
			countOfPages += book.getPages();
			statistic.setCountOfPages(countOfPages);
			statistic.setUser(user);
			statisticRepository.save(statistic);
		}
		userBookRepository.save(userBook);
		return userBook;
	}
	
	

	@Override
	public List<UserBook> getByUserUsername() {
		String loggedUsersUsername = (String) SecurityUtils.getSubject().getPrincipal();
		List<UserBook> usersBooks = userBookRepository.findUserBookByUserUsername(loggedUsersUsername);
		return usersBooks;
	}



	@Override
	public UserBook update(long bookId, String status) {
		String loggedUsersUsername = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(loggedUsersUsername);
		Book book = bookRepository.findById(bookId).get();
		
		UserBook userBook = userBookRepository.findByUserUsernameAndBookId(loggedUsersUsername,bookId);
		userBook.setBook(book);
		userBook.setStatus(status);
		userBook.setUser(user);
		userBookRepository.save(userBook);
		if(status.equals("Read")){
			userBook.setDateOfRead(LocalDate.now());
			Statistic checkStatistic = statisticRepository.findByUserUsername(loggedUsersUsername);
			Statistic statistic;
			if(checkStatistic == null) {
				statistic = new Statistic();
			}else {
				statistic = checkStatistic;
			}
			int countOfPages = statistic.getCountOfPages();
			countOfPages += book.getPages();
			statistic.setCountOfPages(countOfPages);
			statistic.setUser(user);
			statisticRepository.save(statistic);
		}
		return userBook;
	}



	@Override
	public boolean isInMyLibrary(long id) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		UserBook userbook = userBookRepository.findByUserUsernameAndBookId(username, id);
		if(userbook != null) {
			return true;
		}
		return false;
	}



	@Override
	public int countOfBooks(long id) {
		
		User user = userRepository.get(id);
		int count = userBookRepository.findCountOfBooks(user);
		return count;
	}



	@Override
	public List<UserBook> getByUser(long id) {
		User user = userRepository.get(id);
		return userBookRepository.findUserBookByUser(user);
	}



	@Override
	public UserBook getByBookId(long id) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		Book book = bookRepository.findById(id).get();
		return userBookRepository.findByBookAndUser(book,user);
	}



	@Override
	public List<UserBook> findByUserAndStatus(int year) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		String status = "Read";
		
		List<UserBook> fromBase =  userBookRepository.findByUserAndStatus(user, status);
		List<UserBook> books = new ArrayList<UserBook>();
		for(UserBook ub : fromBase) {
			if(ub.getDateOfRead().getYear() == year) {
				books.add(ub);
			}
		}
		return books;
	}



	
	
	

}
