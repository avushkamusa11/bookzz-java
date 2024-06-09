package online.library.repositories;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import online.library.entities.Statistic;
import online.library.entities.User;

@Repository
@Transactional
public class StatisticDaoImpl implements StatisticDao {
	@Autowired 
	UserRepository userRepository;
	@PersistenceContext
	EntityManager entityManager;
	
	@Override
	public int findStatisticByUserUsernameAndDate(String username, Date dateFrom, Date dateTo) {
		  String queryString = "select username ,sum(b.pages) \r\n"
		  		+ "	from user_book ub \r\n"
		  		+ "		join `user` u on u.id = ub.user_id\r\n"
		  		+ "			join (\r\n"
		  		+ "				select id , pages from book\r\n"
		  		+ "			) b on b.id = ub.book_id \r\n"
		  		+ "		where ub.date_of_read >=:dateFrom \r\n"
		  		+ "		and ub.date_of_read <=:dateTo \r\n"
		  		+ "     and u.username =:username "
		  		+ "		group by u.username;\r\n";

			Query query = getSession().createSQLQuery(queryString)
					.setParameter("dateFrom", dateFrom)
					.setParameter("dateTo", dateTo)
			        .setParameter("username", username);
			List<Object[]> obj =(List<Object[]>) query.getResultList();
			
			//Statistic st = new Statistic();
			//User user = userRepository.findUserByUsername(obj[0].toString());
			//st.setUser(user);
			int count = 0; 
			for(Object[] row : obj) {
				count = (Integer.parseInt(row[1].toString()));
			}
			 
			return count;
	}
	
	@Override
	public Date findFirsDate(String username) {
		  String queryString = "select ub.date_of_read from user_book ub \r\n"
		  		+ "	join `user` u on u.id = ub.user_id\r\n"
		  		+ "	where u.username =:username\r\n"
		  		+ "	and ub.status = 'Read'\r\n"
		  		+ "	order by date_of_read;";
		  Query query = getSession().createSQLQuery(queryString)
			        .setParameter("username", username);
			Date date = (Date) query.getSingleResult();
			
			return date;
	}
	

	private Session getSession() {
		 return entityManager.unwrap(Session.class);
	}

}
