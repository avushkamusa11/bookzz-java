package online.library.beans;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatsBean {
    private Long id;
    private String userPicture;
    private String username;
    private String lastMessage;
}
