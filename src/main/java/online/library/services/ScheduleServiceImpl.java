package online.library.services;

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
	public Schedule addSchedue() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		int month =  new Date().getMonth();
		Schedule schedule = new Schedule();
		schedule.setMonth(setMonth(month));
		schedule.setUser(user);
		scheduleRepository.save(schedule);
		return null;
	}
	@Override
	public boolean hasScheduleForMonth() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		int month =  new Date().getMonth();
		
		Schedule schedule =  scheduleRepository.getScheduleByMonthAndUser(setMonth(month),user);
		if(schedule == null) {
			return false;
		}
		return true;
	}
	@Override
	public Plan savePlan(Date date, String startTime, String endTime, long bookId) {
		String[] tempStartDate = startTime.split(" ");
		String currentStartTime = tempStartDate[4];
		String[] tempEndDate = endTime.split(" ");
		String currentEndTime = tempEndDate[4];
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		int month =  new Date().getMonth();
		Book book = bookRepository.findById(bookId).get();
		Schedule schedule =  scheduleRepository.getScheduleByMonthAndUser(setMonth(month),user);
		Plan plan = new Plan();
		plan.setDate(date);
		plan.setStatTime(currentStartTime);
		plan.setEndTime(currentEndTime);
		plan.setSchedule(schedule);
		plan.setBook(book);
		planRepository.save(plan);
		return plan;
	}
	@Override
	public List<Plan> getPlans() {
		String username = (String) SecurityUtils.getSubject().getPrincipal();
		User user = userRepository.findUserByUsername(username);
		int month =  new Date().getMonth();
		
		Schedule schedule =  scheduleRepository.getScheduleByMonthAndUser(setMonth(month),user);
		List<Plan> plan = planRepository.findPlanBySchedule(schedule);
		
		return plan;
	}

}
