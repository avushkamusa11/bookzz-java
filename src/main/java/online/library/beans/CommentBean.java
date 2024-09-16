package online.library.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.library.entities.Book;
import online.library.entities.User;

import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CommentBean {
    private Long id;
    private UserCommentBean user;
    private Long bookId;
    private String comment;
    private String publishDate;
}
