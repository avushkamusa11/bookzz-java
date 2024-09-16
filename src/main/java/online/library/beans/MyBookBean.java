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
public class MyBookBean {
    private Long id;
    private String status;
    private String dateOfRead;
    private Long bookId;
    private String title;
    private String bookImage;
    private List<String> authors;

}
