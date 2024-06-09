package online.library.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.Comment;
import online.library.services.CommentsService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/comments")
public class CommentsController {
	@Autowired
	CommentsService commentsService;

	@GetMapping("/{id}")
	public List<Comment> getCommentsByBookId(@PathVariable(name = "id") String bookId) {
		List<Comment> comments = commentsService.findAllComentsByBookId(Long.parseLong(bookId));
		return comments;
	}
	
	@PostMapping("/{id}")
	public void addComment(@PathVariable(name = "id") String id ,@RequestBody String comment) {
		commentsService.save(comment, Long.parseLong(id));
	}
}
