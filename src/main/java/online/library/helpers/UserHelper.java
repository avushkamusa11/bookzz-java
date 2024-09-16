package online.library.helpers;

import online.library.beans.UserBean;
import online.library.entities.User;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserHelper {
    public UserBean convertToUserBean(User user) throws IOException {
        UserBean bean = new UserBean();
        bean.setId(user.getId());
        bean.setfName(user.getfName());
        bean.setlName(user.getlName());
        if(user.getProfilePicture() != null){
            String profilePicture = FileConverterHelper.convertImageToBase64(user.getProfilePicture());
            bean.setProfilePicture(profilePicture);
        }
        bean.setUsername(user.getUsername());
        return bean;
    }
    public List<UserBean> convertToList(List<User> users) throws IOException {
        List<UserBean> beanList = new ArrayList<>();
        for(User u: users){
            beanList.add(convertToUserBean(u));
        }
        return beanList;
    }

}
