package online.library.services;

import java.time.LocalDateTime;
import java.util.List;

import online.library.entities.Conversation;
import online.library.entities.User;

public interface ConversationService {
	Conversation addConversation( long reciever, String message);
	List<Conversation> getConversation(long reciever);
	List<Conversation> getConversationsBySender();
	List<Conversation> getConversationByUsers(long reciever);
}
