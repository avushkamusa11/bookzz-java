package online.library.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.JoinColumn;


@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String fName;
	private String lName;
	private String username;
	private String password;
	@ManyToOne
	private Role role;
	@OneToMany
	@JsonIgnore
	private List<UserBook> userBook;
	private String profilePicture;
	@ManyToMany
	@JoinTable(name = "user_friends", joinColumns = @JoinColumn (name = "user_id"),
	        inverseJoinColumns = @JoinColumn (name = "friend_user_id"))
	@JsonIgnore
	private List<User> friends = new ArrayList<>();
	private String biography;
	private LocalDate lastActivity;
	private LocalDate singUp;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getfName() {
		return fName;
	}
	public void setfName(String fName) {
		this.fName = fName;
	}
	public String getlName() {
		return lName;
	}
	public void setlName(String lName) {
		this.lName = lName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public List<UserBook> getUserBook() {
		return userBook;
	}
	public void setUserBook(List<UserBook> userBook) {
		this.userBook = userBook;
	}
	public String getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	public List<User> getFriends() {
		return friends;
	}
	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	public String getBiography() {
		return biography;
	}
	public void setBiography(String biography) {
		this.biography = biography;
	}
	public LocalDate getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(LocalDate lastActivity) {
		this.lastActivity = lastActivity;
	}
	public LocalDate getSingUp() {
		return singUp;
	}
	public void setSingUp(LocalDate singUp) {
		this.singUp = singUp;
	}
	
	
	
}
