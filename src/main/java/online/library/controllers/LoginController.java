package online.library.controllers;

import java.time.LocalDate;

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
	public User login( @PathVariable( name = "username") String username,
			@PathVariable( name = "password") String password) {
		if (username != null){
			Subject subject = SecurityUtils.getSubject();
				UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				try {
					token.setRememberMe(true);
					
					subject.login(token);
					//subject.isAuthenticated();
					//subject.isRemembered();
					//User user = userService.findUserByUsernameAndPassword(username, password);
					String loggedUsersUsername = (String) SecurityUtils.getSubject().getPrincipal();
					User user = userService.findUserByUsername(username);
					
					userService.updateActivity(user);
					return user;
				} catch (AuthenticationException ae) {
					throw ae;
				}	
		}
		return null;
	}
	@PostMapping("/logout")
	public User logout() {
		SecurityUtils.getSubject().logout();
		return new User();
		
	}
}
