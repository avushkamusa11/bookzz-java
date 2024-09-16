package online.library.repositories;

import java.util.Date;
import java.util.List;

import online.library.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import online.library.entities.Plan;
import online.library.entities.Schedule;

public interface PlanRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
	List<Plan> findPlanBySchedule(Schedule schedule);

    Plan findByScheduleAndDateAndStatTime(Schedule schedule, Date date, String startTime);
}
