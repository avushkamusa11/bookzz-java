package online.library.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.Month;
import online.library.entities.Schedule;
import online.library.entities.User;


public interface ScheduleRepository  extends JpaRepository<Schedule, Long>, JpaSpecificationExecutor<Schedule>{
	Schedule getScheduleByMonthAndUser(Month month,User user);
}
