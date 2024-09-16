package online.library.controllers;

import online.library.beans.YearGoalBean;
import online.library.helpers.YearGoalHelper;
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

	@Autowired
	YearGoalHelper yearGoalHelper;

	@PostMapping("/{countOfBooks}/{token}")
	public YearGoalBean addGoal(@PathVariable(name = "countOfBooks") String countOfBooks, @PathVariable String token) {
		YearGoal goal = yearGoalService.add(Integer.parseInt(countOfBooks),token);
	return  yearGoalHelper.convertToBean(goal);
	}
	@GetMapping("/myGoal/{token}")
	public YearGoal getGoal(@PathVariable String token) {
		return yearGoalService.getByUserUsername();
	}
	
	@GetMapping("/myGoal/{year}/{token}")
	public YearGoalBean getGoalByYear(@PathVariable(name ="year") String year, @PathVariable String token) {
		YearGoal yearGoal = yearGoalService.getByUserUsernameAndYear(Integer.parseInt(year), token);
		if(yearGoal == null ){
			return new YearGoalBean(-1l,-1,-1);
		}
		return yearGoalHelper.convertToBean(yearGoal);
	}
}
