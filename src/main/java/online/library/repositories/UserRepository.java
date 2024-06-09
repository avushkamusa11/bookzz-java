package online.library.repositories;

import java.util.List;

import online.library.entities.User;

public interface UserRepository {
	User get(long id);

	void create(User user);

	void update(User user);

	User findUserByUsernameAndPassword(String username, String password);

	User findUserByUsername(String username);
	
	List<User> getAllUsers(String username);
	
	void addFriend(User user);
	List<User> getFriends(long id);

	List<User> getUsersNotFriend(String username);
	
}
