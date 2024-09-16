package online.library.helpers;

import online.library.beans.CommentBean;
import online.library.beans.UserCommentBean;
import online.library.entities.Comment;
import online.library.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentHelper {
    @Autowired
    private BookHelper bookHelper;

    public List<CommentBean> convertToBeanList(List<Comment> comments){
        List<CommentBean> output = new ArrayList<>();
        for(Comment c : comments){
            CommentBean bean = convertToBean(c);
            output.add(bean);
        }
        return  output;
    }
    public CommentBean convertToBean(Comment comment){
        CommentBean bean = new CommentBean();
        bean.setId(comment.getId());
        bean.setComment(comment.getComment());
        bean.setPublishDate(comment.getPusblishDate().toString());
        bean.setUser(convertToUserBean(comment.getUser()));
        bean.setBookId(comment.getBook().getId());
        return  bean;
    }

    private UserCommentBean convertToUserBean(User user) {
        UserCommentBean bean = new UserCommentBean();
        bean.setId(user.getId());
        bean.setUsername(user.getUsername());
        bean.setFullName(user.getfName() + " " + user.getlName());
        return bean;
    }
}
