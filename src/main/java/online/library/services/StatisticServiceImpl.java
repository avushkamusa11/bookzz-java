package online.library.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import javax.security.auth.Subject;

import online.library.entities.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Plan;
import online.library.entities.Statistic;
import online.library.repositories.StatisticDao;
import online.library.repositories.StatisticRepository;

@Service
public class StatisticServiceImpl implements StatisticService {
	@Autowired
	private StatisticRepository statisticRepository;
	@Autowired 
	private StatisticDao statisticDao;
	@Autowired
	private UserService userService;
	
	@Override
	public Statistic getStatisticByUsername() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		Statistic statistic = statisticRepository.findByUserUsername(username);
		return statistic;
	}
	@Override
	public int getStatisticByUsernameAndDate(Date from, Date to, String token) {
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.getUserByToken(token);

		return statisticDao.findStatisticByUserUsernameAndDate(user.getUsername(), from, to);
	}
	@Override
	public Date getFirstDate() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		return statisticDao.findFirsDate(username);
	}
	

}
