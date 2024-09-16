package online.library.controllers;

import java.util.List;

import online.library.beans.CommentBean;
import online.library.helpers.CommentHelper;
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

	@Autowired
	CommentHelper commentHelper;

	@GetMapping("/{id}")
	public List<CommentBean> getCommentsByBookId(@PathVariable(name = "id") String bookId) {
		List<Comment> comments = commentsService.findAllComentsByBookId(Long.parseLong(bookId));
		return commentHelper.convertToBeanList(comments);
	}
	
	@PostMapping("/{id}/{token}")
	public Comment addComment(@PathVariable(name = "id") String id , @PathVariable(name = "token") String token,@RequestBody String comment) {
		return commentsService.save(comment,token, Long.parseLong(id));
	}
}
