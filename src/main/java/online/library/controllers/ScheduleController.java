package online.library.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

import online.library.beans.PlanInputBean;
import online.library.beans.PlanOutputBean;
import online.library.helpers.PlanHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import online.library.entities.Plan;
import online.library.entities.Schedule;
import online.library.services.ScheduleService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	@Autowired
	PlanHelper helper;
	
	@PostMapping("/add/{token}")
	public Schedule addSchedule(@PathVariable String token) {
		return scheduleService.addSchedue(token);
	}
	
//	@GetMapping("/check/{token}")
//	public boolean hasScheduleForMonth(@PathVariable String token) {
//		return scheduleService.hasScheduleForMonth(token);
//
//	}
//
	@GetMapping("/getPlans/{token}")
	public List<PlanOutputBean> getPlans(@PathVariable String token) {
		return helper.convertToList(scheduleService.getPlans(token));
		
	}
	
	@PostMapping("/addPlan/{token}")
	public PlanOutputBean savePlan(@PathVariable String token, @RequestBody PlanInputBean input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		try {
			Date date = dateFormat.parse(input.getDate());
			return helper.convertToBean(scheduleService.savePlan(date, input.getStartTime(), input.getTitle(),token));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  null;
		}
}
