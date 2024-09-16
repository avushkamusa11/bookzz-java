package online.library.services;

import java.time.LocalDate;
import java.util.*;

import javax.security.auth.Subject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Role;
import online.library.entities.User;
import online.library.entities.UserBook;
import online.library.repositories.RoleRepository;
import online.library.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;

	
	User user;

	private HashMap<String,User> tokens = new HashMap<>();

	@Override
	public User save(String fName, String lName, String username, String password) {
		user = new User();
		LocalDate joinDate = LocalDate.now();
		Role role = roleRepository.findRoleByRoleName("user");
		user.setfName(fName);
		user.setlName(lName);
		user.setPassword(password);
		user.setUsername(username);
		user.setRole(role);
		user.setSingUp(joinDate);
		userRepository.create(user);
		return user;
	}

	@Override
	public User update(long id,String fName, String lName, String username, String password) {
		user = userRepository.get(id);
		Role role = roleRepository.findRoleByRoleName("user");
		user.setfName(fName);
		user.setlName(lName);
		user.setPassword(password);
		user.setUsername(username);
		user.setRole(role);
		userRepository.update(user);
		return user;
		
	}

	@Override
	public User get(long id) {
		user = userRepository.get(id);
		return user;
	}

	@Override
	public User findUserByUsername(String username) {
		user = userRepository.findUserByUsername(username);
		return user;
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		user = userRepository.findUserByUsernameAndPassword(username, password);
		return user;
	}

	@Override
	public User addProfilePicture(String token, String image) {
		user = getUserByToken(token);
		user.setProfilePicture(image);
		userRepository.update(user);
		return user;
		
	}

	@Override
	public User updateActivity(User currentUser) {
		LocalDate now = LocalDate.now();
		user.setLastActivity(now);
		userRepository.update(currentUser);
		return user;
		
	}

	@Override
	public User updateBiography(String biography) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		user.setBiography(biography);
		userRepository.update(user);
		return user;
		
	}

	@Override
	public List<User> getAllUsers(String token) {
		user = getUserByToken(token);
		List<User> users = userRepository.getAllUsers(user.getUsername());
		List<User> friends = userRepository.getFriends(user.getId());

		for(User u: friends) {
			users.removeIf(user1 -> user1.getId().equals(u.getId()));
		}
		return users;
	}

	@Override
	public List<User> addFriend( long friendId, String token) {
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = getUserByToken(token);
		List<User> friends = userRepository.getFriends(user.getId());
		User friend = userRepository.get(friendId);
		friends.add(friend);
		user.setFriends(friends);
		userRepository.addFriend(user);
		return friends;
		
	}

	@Override
	public List<User> getFriends(String token) {
		User user = getUserByToken(token);
		List<User> friends = userRepository.getFriends(user.getId());
		return friends;
	}

	@Override
	public String addToken(String username, String password) {
		user = userRepository.findUserByUsernameAndPassword(username,password);
		if(user != null){
			return addToken(user);
		}
		return null;
	}

	@Override
	public String addToken(User user) {
		if(tokens.containsValue(user)){
			tokens.remove(user);
		}
		String token = UUID.randomUUID().toString();
		tokens.put(token, user);
		return token;
	}

	@Override
	public User getUserByToken(String token) {
		if(tokens.containsKey(token)){
			return tokens.get(token);
		}
		return null;
	}

	@Override
	public void logout(String token) {
		if(tokens.containsKey(token)){
			tokens.remove(token);
		}
	}

}
