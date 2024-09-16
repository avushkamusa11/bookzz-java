package online.library.services;

import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import javax.security.auth.Subject;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import online.library.entities.Book;
import online.library.entities.Month;
import online.library.entities.Plan;
import online.library.entities.Schedule;
import online.library.entities.User;
import online.library.repositories.BookRepository;
import online.library.repositories.PlanRepository;
import online.library.repositories.ScheduleRepository;
import online.library.repositories.UserRepository;

@Service
public class ScheduleServiceImpl implements ScheduleService {
	@Autowired
	private UserService userService;
	@Autowired
	private ScheduleRepository scheduleRepository;
	@Autowired 
	private PlanRepository planRepository;
	@Autowired 
	private UserRepository userRepository;
	@Autowired BookRepository bookRepository;
	public Month setMonth(int month) {
		Month stringMonth = Month.January;
		switch(month) {
		  
		  case 0:
			    stringMonth = Month.January;
			    break;
		  case 1:
			  stringMonth = Month.February;
			    break;
		  case 2:
			  stringMonth = Month.March;
			    break;
		  case 3:
			  stringMonth = Month.April;
			    break;
		  case 4:
			  stringMonth = Month.May;
			    break;
		  case 5:
			  stringMonth = Month.June;
			    break;
		  case 6:
			  stringMonth = Month.July;
			    break;
		  case 7:
			  stringMonth = Month.August;
			    break;
		  case 8:
			  stringMonth = Month.September;
			    break;
		  case 9:
			  stringMonth = Month.October;
			    break;
		  case 10:
			  stringMonth = Month.November;
			    break;
		  case 11:
			  stringMonth = Month.December;
			    break;
		}
		return stringMonth;
	}
	@Override
	public Schedule addSchedue(String token) {

		User user = userService.getUserByToken(token);

		int month =  new Date().getMonth();
		if(hasScheduleForMonth(user, month)){
			return null;
		}else{
			Schedule schedule = new Schedule();
			schedule.setMonth(setMonth(month));
			schedule.setUser(user);
			scheduleRepository.save(schedule);
			return null;
		}
	}
	@Override
	public boolean hasScheduleForMonth(User user, int month) {
		Schedule schedule =  scheduleRepository.getScheduleByMonthAndUser(setMonth(month),user);
		if(schedule == null) {
			return false;
		}
		return true;
	}
	@Override
	public Plan savePlan(Date date, String startTime, String title, String token) {
		User user = userService.getUserByToken(token);
		System.out.println("Default Charset=" + Charset.defaultCharset());
 		int month =  new Date().getMonth();
		Book book = bookRepository.findByTitle(title);
		Schedule schedule =  scheduleRepository.getScheduleByMonthAndUser(setMonth(month),user);
		Plan plan = planRepository.findByScheduleAndDateAndStatTime(schedule,date,startTime);
		if(plan != null){
			Plan emptyPlan = new Plan();
			emptyPlan.setId(-1l);
			return  emptyPlan;
		}
		Plan newPlan = new Plan();
		newPlan.setDate(date);
		newPlan.setStatTime(startTime);
		newPlan.setSchedule(schedule);
		newPlan.setBook(book);
		planRepository.save(newPlan);
		return newPlan;
	}
	@Override
	public List<Plan> getPlans(String token) {
		User user = userService.getUserByToken(token);
		int month =  new Date().getMonth();
		
		Schedule schedule =  scheduleRepository.getScheduleByMonthAndUser(setMonth(month),user);
		List<Plan> plan = planRepository.findPlanBySchedule(schedule);
		
		return plan;
	}

}
