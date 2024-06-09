package online.library.controllers;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.Plan;
import online.library.entities.Schedule;
import online.library.services.ScheduleService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@PostMapping("/add")
	public Schedule addSchedule() {
		return scheduleService.addSchedue();
	}
	
	@GetMapping("/check")
	public boolean hasScheduleForMonth() {
		return scheduleService.hasScheduleForMonth();
		
	}
	
	@GetMapping("/getPlans")
	public List<Plan> getPlans() {
		return scheduleService.getPlans();
		
	}
	
	@PostMapping("/addPlan/{date}/{startTime}/{endTime}/{bookId}")
	public Plan savePlan(@PathVariable(name ="date") Date date, @PathVariable(name = "startTime") String startTime, @PathVariable(name = "endTime") String endTime, @PathVariable(name = "bookId") String bookId) {
		return scheduleService.savePlan(date, startTime, endTime, Long.parseLong(bookId));
		
	}
}
