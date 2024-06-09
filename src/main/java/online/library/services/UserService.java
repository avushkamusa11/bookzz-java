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
	public User addProfilePicture(long id , String image);
	public List<User> getAllUsers();

	List<User> addFriend(long friendId);
	List<User> getFriends(long id);
}
