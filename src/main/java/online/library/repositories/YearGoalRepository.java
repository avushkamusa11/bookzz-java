package online.library.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.YearGoal;

public interface YearGoalRepository extends JpaRepository<YearGoal, Long>, JpaSpecificationExecutor<YearGoal> {
	public YearGoal findByUserUsername(String username);
	public YearGoal findByUserUsernameAndYear(String username, int year);
}
