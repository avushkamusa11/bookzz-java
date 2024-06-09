package online.library.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	public User addProfilePicture(long id, String image) {
		user = userRepository.get(id);
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
	public List<User> getAllUsers() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		user = userRepository.findUserByUsername(username);
		List<User> users = userRepository.getAllUsers(username);
		List<User> friends = userRepository.getFriends(user.getId());

		for(User u: friends) {
			users.removeIf(user1 -> user1.getId().equals(u.getId()));
		}
		return users;
	}

	@Override
	public List<User> addFriend( long friendId) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		List<User> friends = userRepository.getFriends(user.getId());
		User friend = userRepository.get(friendId);
		friends.add(friend);
		user.setFriends(friends);
		userRepository.addFriend(user);
		return friends;
		
	}

	@Override
	public List<User> getFriends(long id) {
		
		List<User> friends = userRepository.getFriends(id);
		return friends;
	}

}
