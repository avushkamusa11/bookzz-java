package online.library.controllers;

import java.util.List;

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
	
	@GetMapping("/get/{id}")
	public User getUser(@PathVariable(name = "id") String id){
		User user = new User();
		user = userService.get(Long.parseLong(id));
		return user;
	}
	
	@PostMapping("/{fName}/{lName}/{username}/{password}")
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
	
	@GetMapping("/all")
	public List<User> getAllUsers() {
		
		return userService.getAllUsers();
	}
	
	@PutMapping("addFriend/{friendId}")
	public List<User> addFriend(@PathVariable(name = "friendId") String friendId) {
		return userService.addFriend( Long.parseLong(friendId));
	
		
	}
	
	@GetMapping("/getFriends/{id}")
	public List<User> getFriends(@PathVariable(name = "id") String id){
		return userService.getFriends(Long.parseLong(id));
	}
	

}
