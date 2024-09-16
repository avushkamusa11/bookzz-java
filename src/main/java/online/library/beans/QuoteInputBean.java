package online.library.beans;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuoteInputBean {
    private String quoteText;
    private String bookTitle;
    @JsonProperty("isPrivate")
    private boolean isPrivate;
}
