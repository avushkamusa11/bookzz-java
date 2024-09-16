package online.library.services;

import java.time.LocalDate;
import java.util.List;

import online.library.entities.User;

public interface UserService {
	public User save(String fName, String lName, String username, String password);
	public User update(long id,String fName, String lName, String username, String password);
	public User  updateActivity(User user);
	public User updateBiography(String biography);
	public User get(long id);
	public User findUserByUsername(String username);
	public User findUserByUsernameAndPassword(String username, String password);
	public User addProfilePicture(String token , String image);
	public List<User> getAllUsers(String token);

	List<User> addFriend(long friendId, String token);
	List<User> getFriends(String token);
	String addToken(String username, String password);
	String addToken(User user);
	User getUserByToken(String token);

	void logout(String token);
}
