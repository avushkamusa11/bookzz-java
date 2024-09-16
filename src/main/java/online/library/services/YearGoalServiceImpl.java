package online.library.services;

import java.time.LocalDate;

import javax.security.auth.Subject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.User;
import online.library.entities.YearGoal;
import online.library.repositories.UserRepository;
import online.library.repositories.YearGoalRepository;

@Service
public class YearGoalServiceImpl implements YearGoalService {
	
	@Autowired
	YearGoalRepository yearGoalRepository;
	@Autowired
	UserService userService;
	@Override
	public YearGoal add(int numberOfBooks, String token) {
		// String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.getUserByToken(token);
		YearGoal yearGoal = new YearGoal();
		LocalDate date = LocalDate.now();
		yearGoal.setYear(date.getYear());
		yearGoal.setCount(numberOfBooks);
		yearGoal.setUser(user);
		yearGoalRepository.save(yearGoal);
		return yearGoal;
	}

	@Override
	public YearGoal getByUserUsername() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		return yearGoalRepository.findByUserUsername(username);
		
	}



	@Override
	public YearGoal getByUserUsernameAndYear(int year, String token) {
		//String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userService.getUserByToken(token);
		YearGoal yg = yearGoalRepository.findByUserUsernameAndYear(user.getUsername(), year);
		return yg;
	}

}
