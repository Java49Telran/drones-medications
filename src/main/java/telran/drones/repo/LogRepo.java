package telran.drones.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import telran.drones.dto.DroneMedicationsAmount;
import telran.drones.dto.State;
import telran.drones.entities.*;
import telran.drones.service.MedicationCode;

public interface LogRepo extends JpaRepository<EventLog, Long> {

	List<EventLog> findByDroneNumberAndState(String droneNumber, State state);

	List<EventLog> findByDroneNumberOrderByTimestampDesc(String droneNumber);
	
@Query("select d.number as number, count(log.drone) as amount from EventLog log"
		+ " right  join log.drone d "
		+ " where log.state='LOADING' or log.drone is null group by d.number "
		+ "order by count(log.drone) desc ")
	List<DroneMedicationsAmount> findDronesAmounts();
/****************************************************************/


EventLog findFirst1ByDroneNumberOrderByTimestampDesc(String number);

}
