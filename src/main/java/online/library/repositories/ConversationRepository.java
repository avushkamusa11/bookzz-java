package online.library.repositories;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import online.library.entities.Conversation;
import online.library.entities.User;


public interface ConversationRepository extends JpaRepository<Conversation, Long>, JpaSpecificationExecutor<Conversation> {
	List<Conversation> getConversationBySenderAndReciever(User sender, User reciever);
	@Query("SELECT c FROM Conversation c where sender_id=:sender and reciever_id = :reciever or sender_id =:reciever and reciever_id =:sender order by send_time asc")
	List<Conversation> getConversationByUsers(long sender, long reciever);
	List<Conversation> getConversationBySender(User sender);

}
