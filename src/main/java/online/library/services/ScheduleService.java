package online.library.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import online.library.entities.Month;
import online.library.entities.Plan;
import online.library.entities.Schedule;
import online.library.entities.User;

public interface ScheduleService {
	Schedule addSchedue(String token);
	boolean hasScheduleForMonth(User user,int month);

	public Plan savePlan(Date date, String startTime, String title, String token);
	public List<Plan> getPlans(String token);
}
