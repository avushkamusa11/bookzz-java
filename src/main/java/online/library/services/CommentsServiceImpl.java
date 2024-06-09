package online.library.services;

import java.util.List;



import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Book;
import online.library.entities.Comment;
import online.library.entities.User;
import online.library.repositories.BookRepository;
import online.library.repositories.CommentRepository;
import online.library.repositories.UserRepository;

@Service
public class CommentsServiceImpl implements CommentsService {
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BookRepository bookRepository;

	@Override
	public List<Comment> findAllComentsByBookId(long id) {
		List<Comment> comments = commentRepository.findCommentsByBookId(id);
		return comments;
	}

	@Override
	public void save(String commentText, long id) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		Book book = bookRepository.findById(id).get();
		Comment comment = new Comment();
		comment.setBook(book);
		comment.setComment(commentText);
		comment.setUser(user);
		commentRepository.save(comment);
		
	}

}
