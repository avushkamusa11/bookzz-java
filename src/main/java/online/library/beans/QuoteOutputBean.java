package online.library.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuoteOutputBean {
    private Long id;
    private String quoteText;
    private String bookTitle;
    private String username;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
    private String userProfilePicture;
}
