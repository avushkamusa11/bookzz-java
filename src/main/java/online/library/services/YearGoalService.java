package online.library.services;

import online.library.entities.YearGoal;

public interface YearGoalService {
	public YearGoal add(int numberOfBooks, String token);
	public YearGoal getByUserUsername();
	public YearGoal getByUserUsernameAndYear(int year, String token);
}
