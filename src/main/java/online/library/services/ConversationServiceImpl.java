package online.library.services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Conversation;
import online.library.entities.User;
import online.library.repositories.ConversationRepository;
import online.library.repositories.UserRepository;

@Service
public class ConversationServiceImpl implements ConversationService {
	@Autowired
	ConversationRepository conversationRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;
	
	@Override
	public Conversation addConversation(long recieverId, String message, String token) {
		LocalDateTime time = LocalDateTime.now();
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		User sender = userService.getUserByToken(token);
		User reciever = userRepository.get(recieverId);
		Conversation conversation = new Conversation();
		conversation.setSender(sender);
		conversation.setReciever(reciever);
		conversation.setMessage(message);
		conversation.setSendTime(time);
		conversationRepository.save(conversation);
		return conversation;
	}

	@Override
	public List<Conversation> getConversation(long recieverId) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User sender = userRepository.findUserByUsername(username);
		User reciever = userRepository.get(recieverId);
		List<Conversation> conversation = conversationRepository.getConversationBySenderAndReciever(sender, reciever);
		return conversation;
	}

	@Override
	public List<Conversation> getConversationsBySender() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User sender = userRepository.findUserByUsername(username);
		List<Conversation> conversation = conversationRepository.getConversationBySender(sender);
		return conversation;
	}

	@Override
	public List<Conversation> getConversationByUsers(long reciever, String token) {
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		User sender = userService.getUserByToken(token);
		List<Conversation> conversation = conversationRepository.getConversationByUsers(sender.getId(), reciever);
		return conversation;
	}

	@Override
	public List<Conversation> getAllLastConversations(String token) {
		User user = userService.getUserByToken(token);
		List<Conversation> lastMessages = new ArrayList<>();
		List<User> friends = userService.getFriends(token);
		for (User u : friends) {
			List<Conversation> c = conversationRepository.getConversationByUsersDesc(user.getId(), u.getId());
			if(!c.isEmpty()){
				lastMessages.add(c.get(0));
			}

		}
		return lastMessages;
	}

}
