package telran.drones.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.drones.dto.DroneMedicationsAmount;
import telran.drones.dto.State;
import telran.drones.entities.*;

public interface LogRepo extends JpaRepository<EventLog, Long> {

	List<EventLog> findByDroneNumberAndDroneState(String droneNumber, State state);

	List<EventLog> findByDroneNumber(String droneNumber);
@Query("select d.number as number, count(log.drone) as amount from EventLog log"
		+ " right  join log.drone d "
		+ " where d.state='LOADING' or log.drone is null group by d.number "
		+ "order by count(d.state) ")
	List<DroneMedicationsAmount> findDronesAmounts();

}
