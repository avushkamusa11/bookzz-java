package online.library.helpers;

import online.library.beans.BookBean;
import online.library.beans.BookOutputBean;
import online.library.entities.Author;
import online.library.entities.Book;
import online.library.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class BookHelper {
    @Autowired
    private BookService bookService;

    public List<BookBean> convertToList(List<Book> books){
        List<BookBean> outputBean = new ArrayList<>();
        for(Book book : books){
            try {
                outputBean.add(convertToBookBean(book));
            } catch (MalformedURLException e) {
                throw new RuntimeException(e);
            }
        }
        return  outputBean;
    }
    public BookBean convertToBookBean(Book book) throws MalformedURLException {
        BookBean bookBean = new BookBean();
        bookBean.setId(book.getId());
        bookBean.setTitle(book.getTitle());
        bookBean.setRate(book.getRate());
        List<String> authors = new ArrayList<>();
        for(Author author : book.getAuthors()){
            authors.add(author.getfName());
        }
        bookBean.setAuthors(authors);
        if (book.getFile() != null) {
            String base64Image = null;
            try {
                base64Image = FileConverterHelper.convertImageToBase64(book.getFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bookBean.setFile(base64Image);
        }

        return bookBean;
    }

    public BookOutputBean convertToBookOutputBean(Book book) throws MalformedURLException {
        BookOutputBean bookBean = new BookOutputBean();
        bookBean.setId(book.getId());
        bookBean.setTitle(book.getTitle());
        bookBean.setIsbn(book.getIsbn());
        bookBean.setDescription(book.getDescription());
        bookBean.setGenre(book.getGenre() != null ? book.getGenre().getGenreName() : null);
        bookBean.setPages(book.getPages());
        bookBean.setRate(book.getRate());
        List<String> authors = new ArrayList<>();
        for(Author author : book.getAuthors()){
            authors.add(author.getfName());
        }
        bookBean.setAuthors(authors);
        if (book.getFile() != null) {
            String base64Image = null;
            try {
                base64Image = FileConverterHelper.convertImageToBase64(book.getFile());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            bookBean.setFile(base64Image);
        }

        return bookBean;
    }


}
