package online.library.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import online.library.entities.Statistic;
import online.library.services.StatisticService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/statistic")
public class StatisticController {
	
	@Autowired
	StatisticService statisticServise;
	
	@GetMapping("/{fromDate}/{toDate}")
	public int stats(@PathVariable(name = "fromDate") Date fromDate, @PathVariable(name = "toDate") Date toDate) throws ParseException {
		//Date from=new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		//Date to=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		int statistic = statisticServise.getStatisticByUsernameAndDate(fromDate, toDate);
		return statistic;
	}
	
	@GetMapping("/getDate")
	public Date getFirstDate() {
		return statisticServise.getFirstDate();
		
	}
}
