package online.library.services;

import java.util.List;

import online.library.entities.Comment;

public interface CommentsService {
	List<Comment> findAllComentsByBookId(long id);
	
	void save(String comment, long id);

}
