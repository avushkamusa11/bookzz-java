package online.library.controllers;

import java.io.IOException;
import java.util.List;

import online.library.beans.UserBean;
import online.library.helpers.UserHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.User;
import online.library.repositories.UserRepository;
import online.library.services.UserService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserService userService;
	@Autowired
	UserHelper userHelper;
	
	@GetMapping("/get/{id}")
	public User getUser(@PathVariable(name = "id") String id){
		User user = new User();
		user = userService.get(Long.parseLong(id));
		return user;
	}
	
	@GetMapping("/{fName}/{lName}/{username}/{password}")
	public User saveUser(@PathVariable(name = "fName") String fName,@PathVariable(name = "lName") String lName,
			@PathVariable(name = "username") String username, @PathVariable(name = "password") String password) {
		User user  = userService.save(fName, lName, username, password);
				return user;
		
	}
	
	@PutMapping("/{biography}")
	public User addBiography(@PathVariable(name = "biography") String biography) {
		User user = userService.updateBiography(biography);
		return user;
	}
	
	@GetMapping("/{username}")
	public User getUserByUsername(@PathVariable(name = "username") String username) {
		User user = userService.findUserByUsername(username);
		return user;
	}
	
	@GetMapping("/all/{token}")
	public List<UserBean> getAllUsers(@PathVariable String token) throws IOException {
		return userHelper.convertToList(userService.getAllUsers(token));
	}
	
	@PutMapping("/addFriend/{friendId}/{token}")
	public List<UserBean> addFriend(@PathVariable(name = "friendId") String friendId, @PathVariable String token) throws IOException {
		return userHelper.convertToList(userService.addFriend( Long.parseLong(friendId), token));
	}
	
	@GetMapping("/getFriends/{token}")
	public List<UserBean> getFriends(@PathVariable(name = "token") String token) throws IOException {
		return userHelper.convertToList(userService.getFriends(token));
	}
	

}
