package online.library.helpers;

import online.library.beans.MyBookBean;
import online.library.entities.Author;
import online.library.entities.UserBook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserBookHelper {
   public List<MyBookBean> convertToMyBookList(List<UserBook> books) throws IOException {
       List<MyBookBean> listBean = new ArrayList<>();
       for(UserBook ub : books){
           MyBookBean myBook = convertToMyBookBean(ub);
           listBean.add(myBook);
       }
       return listBean;
   }
    public MyBookBean convertToMyBookBean(UserBook ub) throws IOException {
        MyBookBean bean = new MyBookBean();
        bean.setId(ub.getId());
        bean.setStatus(ub.getStatus());
        bean.setDateOfRead(ub.getDateOfRead() != null ? ub.getDateOfRead().toString() : "");
        bean.setBookId(ub.getBook().getId());
        bean.setBookImage(FileConverterHelper.convertImageToBase64(ub.getBook().getFile()));
        bean.setTitle(ub.getBook().getTitle());
        List<String> authors = new ArrayList<>();
        for(Author a : ub.getBook().getAuthors()){
            authors.add(a.getfName());
        }
        bean.setAuthors(authors);
        return bean;
    }
}
