package online.library.controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import online.library.beans.StatisticInputBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import online.library.entities.Statistic;
import online.library.services.StatisticService;

@CrossOrigin(origins = "http://localhost:3000/")
@RestController
@RequestMapping("/statistic")
public class StatisticController {
	
	@Autowired
	StatisticService statisticServise;
	
	@GetMapping("/{fromDate}/{toDate}/{token}")
	public int stats(@PathVariable(name = "fromDate") Date fromDate, @PathVariable(name = "toDate") Date toDate, @PathVariable String token) throws ParseException {
		//Date from=new SimpleDateFormat("yyyy-MM-dd").parse(fromDate);
		//Date to=new SimpleDateFormat("yyyy-MM-dd").parse(toDate);
		int statistic = statisticServise.getStatisticByUsernameAndDate(fromDate, toDate, token);
		return statistic;
	}

	@GetMapping("/get/{from}/{to}/{token}")
	public int stats(@PathVariable String token, @PathVariable String from, @PathVariable String to) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		Date fromDate = dateFormat.parse(from);
		Date toDate = dateFormat.parse(to);
		int statistic = statisticServise.getStatisticByUsernameAndDate(fromDate, toDate, token);
		return statistic;
	}
	
	@GetMapping("/getDate")
	public Date getFirstDate() {
		return statisticServise.getFirstDate();
		
	}
}
