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
	UserRepository userRepository;
	@Override
	public YearGoal add(int numberOfBooks) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
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
	public YearGoal getByUserUsernameAndYear(int year) {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		YearGoal yg = yearGoalRepository.findByUserUsernameAndYear(username, year);
		if(yg == null) {
			yg = new YearGoal();
		}
		return yg;
	}

}
