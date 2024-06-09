package online.library.beans;

import java.time.LocalDateTime;

import online.library.entities.User;

public class ConversationInput {
	private long reciever;
	private String message;

	public long getReciever() {
		return reciever;
	}
	public void setReciever(long reciever) {
		this.reciever = reciever;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
