package online.library.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.YearGoal;
import online.library.services.YearGoalService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/goal")
public class YearGoalController {
	
	@Autowired 
	YearGoalService yearGoalService;
	@PostMapping("/{countOfBooks}")
	public YearGoal addGoal(@PathVariable(name = "countOfBooks") String countOfBooks) {
		return yearGoalService.add(Integer.parseInt(countOfBooks));
	}
	@GetMapping("/myGoal")
	public YearGoal getGoal() {
		return yearGoalService.getByUserUsername();
	}
	
	@GetMapping("/myGoal/{year}")
	public YearGoal getGoalByYear(@PathVariable(name ="year") String year) {
		YearGoal yearGoal = yearGoalService.getByUserUsernameAndYear(Integer.parseInt(year));
		return yearGoal;
	}
}
