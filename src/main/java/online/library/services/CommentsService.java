package online.library.services;

import java.util.List;

import online.library.entities.Comment;

public interface CommentsService {
	List<Comment> findAllComentsByBookId(long id);
	
	Comment save(String comment,String token, long id);

}
