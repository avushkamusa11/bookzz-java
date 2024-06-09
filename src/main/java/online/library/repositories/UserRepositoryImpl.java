package online.library.repositories;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import online.library.entities.User;

@Repository
@Transactional
public class UserRepositoryImpl implements UserRepository {
	
	@PersistenceContext
	EntityManager entityManager;

	@Override
	public User get(long id) {
		User user = entityManager.find(User.class, new Long(id));
		entityManager.detach(user);
		return user;
	}

	@Override
	public void create(User user) {
		entityManager.persist(user);
	}

	@Override
	public void update(User user) {
		entityManager.merge(user);
	}

	@Override
	public User findUserByUsernameAndPassword(String username, String password) {
		Query query = (Query) entityManager.createQuery("from User where username = :username and password=:password");
		query.setParameter("username", username);
		query.setParameter("password", password);
		List<User> users = query.getResultList();
		User user;
		if(users.isEmpty()) {
			user = null;
		}else {
			user = users.get(0);
		}
		
		return user;

	}

	@SuppressWarnings("unchecked")
	@Override
	public User findUserByUsername(String username) {
		Query query = entityManager.createQuery("from User where username = :username");
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		return !users.isEmpty() ? users.get(0) : null;
	}

	@Override
	public List<User> getAllUsers(String username) {
		Query query = entityManager.createQuery("from User where username is not 'admin' and username is not :username");
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		return users;
	}
	
	@Override
	public List<User> getUsersNotFriend(String username) {
		Query query = entityManager.createQuery("from User where username is not 'admin' and username is not :username");
		query.setParameter("username", username);
		List<User> users = query.getResultList();
		return users;
	}

	@Override
	public void addFriend(User user) {
		entityManager.merge(user);
		
		
	}

	@Override
	public List<User> getFriends(long id) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<User> mainQuery = cb.createQuery(User.class);
		Root<User> userRoot = mainQuery.from(User.class);
		mainQuery.select(userRoot.get("friends"));
		mainQuery.where(cb.equal(userRoot.get("id"), id));
		TypedQuery<User> query = entityManager.createQuery(mainQuery);
		List<User> users = query.getResultList();
		return users;
	}

	
}
