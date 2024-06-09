package online.library.repositories;

import java.util.Date;

import online.library.entities.Statistic;

public interface StatisticDao {
	int findStatisticByUserUsernameAndDate(String username, Date dateFrom, Date dateTo);
	Date findFirsDate(String username);
}
