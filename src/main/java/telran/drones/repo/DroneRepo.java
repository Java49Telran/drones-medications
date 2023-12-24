package telran.drones.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.drones.dto.State;
import telran.drones.entities.*;

public interface DroneRepo extends JpaRepository<Drone, String> {

	List<Drone> findByStateAndBatteryCapacityGreaterThanEqual(State idle, byte capacityThreshold);

}
