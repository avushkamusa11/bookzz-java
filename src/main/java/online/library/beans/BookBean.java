package online.library.beans;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import online.library.entities.Author;
import online.library.entities.Genre;
import online.library.entities.UserBook;
import org.springframework.core.io.Resource;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookBean {
    private Long id;
    private String title;
    private String file;
    private List<String> authors;
    private double rate;
}
