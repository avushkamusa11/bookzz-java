package online.library.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import online.library.entities.Month;
import online.library.entities.Plan;
import online.library.entities.Schedule;

public interface ScheduleService {
	Schedule addSchedue();
	boolean hasScheduleForMonth();

	public Plan savePlan(Date date, String startTime, String endTime, long bookId);
	public List<Plan> getPlans();
}
