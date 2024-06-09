package online.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;


import online.library.entities.Plan;
import online.library.entities.Schedule;

public interface PlanRepository extends JpaRepository<Plan, Long>, JpaSpecificationExecutor<Plan> {
	List<Plan> findPlanBySchedule(Schedule schedule);
}
