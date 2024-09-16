package online.library.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

import online.library.entities.Plan;
import online.library.entities.Statistic;

public interface StatisticService {
	public Statistic getStatisticByUsername();
	public Date getFirstDate();
	public int getStatisticByUsernameAndDate(Date from, Date to, String token);

}
