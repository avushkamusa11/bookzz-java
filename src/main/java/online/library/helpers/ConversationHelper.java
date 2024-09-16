package online.library.helpers;

import online.library.beans.ChatBean;
import online.library.beans.ConversationBean;
import online.library.entities.Conversation;
import online.library.entities.User;
import online.library.services.ConversationService;
import online.library.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationHelper {

    @Autowired
    private UserService userService;
    public List<ConversationBean> convertToList(List<Conversation> conversations, String token) throws IOException {
        List<ConversationBean> beanList = new ArrayList<>();
        for (Conversation c: conversations) {
            beanList.add(convertToBean(c, token));
        }
        return  beanList;
    }

    public ConversationBean convertToBean(Conversation conversation, String token) throws IOException {
        ConversationBean bean = new ConversationBean();
        bean.setId(conversation.getId());
        bean.setMessage(conversation.getMessage());
        if(userService.getUserByToken(token).getId() == conversation.getSender().getId()){
            bean.setUsername(conversation.getReciever().getUsername());
            String profilePicture = FileConverterHelper.convertImageToBase64(conversation.getReciever().getProfilePicture());
            bean.setUserId(conversation.getReciever().getId());
            bean.setUserProfilePicture(profilePicture);
        }else{
            bean.setUsername(conversation.getSender().getUsername());
            String profilePicture = FileConverterHelper.convertImageToBase64(conversation.getSender().getProfilePicture());
            bean.setUserId(conversation.getSender().getId());
            bean.setUserProfilePicture(profilePicture);
        }

        // bean.setUserProfilePicture(con);
        return bean;
    }
    public ChatBean convertToChatBean(Conversation conversation,String token){
        ChatBean bean = new ChatBean();
        User user = userService.getUserByToken(token);
        if(conversation.getSender().getId() == user.getId()){
            bean.setSender(true);
        }else{
            bean.setSender(false);
        }
        bean.setMessageText(conversation.getMessage());
        return bean;
    }

    public List<ChatBean> convertToChatBeanList(List<Conversation> conversations, String token){
        List<ChatBean> bean = new ArrayList<>();
        for(Conversation c : conversations){
            bean.add(convertToChatBean(c,token));
        }
        return  bean;
    }
}
