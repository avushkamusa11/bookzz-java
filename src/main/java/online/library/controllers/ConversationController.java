package online.library.controllers;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.mail.search.ReceivedDateTerm;

import online.library.beans.ChatBean;
import online.library.beans.ConversationBean;
import online.library.helpers.ConversationHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.beans.ConversationInput;
import online.library.entities.Conversation;
import online.library.entities.User;
import online.library.services.ConversationService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/conversation")
public class ConversationController {
	
	@Autowired
	ConversationService conversationService;
	@Autowired
	ConversationHelper conversationHelper;
	
	@PostMapping("/add/{token}")
	public Conversation addConversation(@PathVariable String token, @RequestBody ConversationInput conversation) {
		long reciever;
		String message;
		reciever = conversation.getReciever();
		message = conversation.getMessage();
		
		return conversationService.addConversation(reciever, message, token);
		
	}
	
	@GetMapping("/getOneConversation/{recieverId}/{token}")
	public List<ChatBean> getOneConversation(@PathVariable(name = "recieverId") String recieverId, @PathVariable String token){
		List<Conversation> conversations = conversationService.getConversationByUsers(Long.parseLong(recieverId),token);
	return conversationHelper.convertToChatBeanList(conversations,token);
	}
	
	@GetMapping("/getAllConversationByUser")
	public List<Conversation> getAlConversationByUser(){
		return conversationService.getConversationsBySender();
	}

	@GetMapping("/all/last/{token}")
	public List<ConversationBean> getAllByToken(@PathVariable String token) throws IOException {
		List<Conversation> conversations = conversationService.getAllLastConversations(token);
		return conversationHelper.convertToList(conversations,token);
	}
}
