package online.library.controllers;

import java.io.IOException;
import java.time.LocalDate;

import online.library.beans.UserBean;
import online.library.helpers.FileConverterHelper;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.User;
import online.library.services.UserService;



@CrossOrigin(origins = "http://localhost:3000/")
@RestController
public class LoginController {
	@Autowired
	UserService userService;
	@PostMapping("/login/{username}/{password}")
	public UserBean login(@PathVariable( name = "username") String username,
						  @PathVariable( name = "password") String password) {
		if (username != null){
			Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				try {
					token.setRememberMe(true);
					
					subject.login(token);
					User user = userService.findUserByUsername(username);
					String userToken = userService.addToken(user);
					UserBean userBean = new UserBean();
					userBean.setId(user.getId());
					userBean.setToken(userToken);
					userBean.setUsername(user.getUsername());
					userBean.setfName(user.getfName());
					userBean.setlName(user.getlName());
					if(user.getProfilePicture() != null){
						userBean.setProfilePicture(FileConverterHelper.convertImageToBase64(user.getProfilePicture()));
					}
					userService.updateActivity(user);

					return userBean;
				} catch (AuthenticationException ae) {
					throw ae;
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}
		return null;
	}
	@PostMapping("/logout/{token}")
	public User logout(@PathVariable String token) {
		userService.logout(token);
		return new User();
		
	}
}
