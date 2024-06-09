package online.library.repositories;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import online.library.entities.Statistic;



public interface StatisticRepository extends JpaRepository<Statistic, Long>, JpaSpecificationExecutor<Statistic> {
	Statistic findByUserUsername(String username);

}
