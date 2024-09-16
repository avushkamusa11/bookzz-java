package online.library.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConversationBean {
    private Long id;
    private String username;
    private String userProfilePicture;
    private String message;
    private Long userId;

}
