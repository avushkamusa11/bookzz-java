package online.library.services;

import java.time.LocalDateTime;
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
	
	@Override
	public Conversation addConversation(long recieverId, String message) {
		LocalDateTime time = LocalDateTime.now();
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User sender = userRepository.findUserByUsername(username);
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
	public List<Conversation> getConversationByUsers(long reciever) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User sender = userRepository.findUserByUsername(username);
		List<Conversation> conversation = conversationRepository.getConversationByUsers(sender.getId(), reciever);
		return conversation;
	}

}
