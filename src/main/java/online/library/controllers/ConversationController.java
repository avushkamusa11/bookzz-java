package online.library.controllers;

import java.time.LocalDateTime;
import java.util.List;

import javax.mail.search.ReceivedDateTerm;

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
	
	@PostMapping("/add")
	public Conversation addConversation(@RequestBody ConversationInput conversation) {
		long reciever;
		String message;
		reciever = conversation.getReciever();
		message = conversation.getMessage();
		
		return conversationService.addConversation(reciever, message);
		
	}
	
	@GetMapping("/getOneConversation/{recieverId}")
	public List<Conversation> getOneConversation(@PathVariable(name = "recieverId") String recieverId){
		return conversationService.getConversationByUsers(Long.parseLong(recieverId));
	}
	
	@GetMapping("/getAllConversationByUser")
	public List<Conversation> getAlConversationByUser(){
		return conversationService.getConversationsBySender();
	}
}
