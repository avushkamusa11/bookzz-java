package online.library.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookOutputBean {
    private Long id;
    private String title;
    private String isbn;
    private int pages;
    private String file;
    private String genre;
    private List<String> authors;
    private String description;
    private double rate;
    private String bookPdf;
    private Long readers;
}
