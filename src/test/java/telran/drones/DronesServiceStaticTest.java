package telran.drones;
import telran.drones.api.*;
import telran.drones.dto.State;
import telran.drones.entities.Drone;
import telran.drones.entities.EventLog;
import telran.drones.exceptions.DroneNotFoundException;
import telran.drones.exceptions.IllegalDroneStateException;
import telran.drones.exceptions.MedicationNotFoundException;
import telran.drones.repo.*;
import telran.drones.service.DronesService;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
@SpringBootTest(properties = {PropertiesNames.PERIODIC_UNIT_MICROS  + "=1000000"})
@Sql(scripts = "test_data.sql")
//('Drone-1', 'Middleweight', 300, 100, 'IDLE'),
//('Drone-2', 'Middleweight', 300, 20, 'IDLE'),
//('Drone-3', 'Middleweight', 300, 100, 'LOADING');
//('MED_1', 'Medication-1', 200),
//('MED_2', 'Medication-2', 350)	
class DronesServiceStaticTest {
	private static final String DRON1 = "Drone-1";
	private static final String MED1 = "MED_1";
	private static final String DRON3 = "Drone-3";
	private static final String SERVICE_TEST = "Service: ";
	@Autowired
 DronesService dronesService;
	@Autowired
	DroneRepo droneRepo;
	@Autowired
	LogRepo logRepo;
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_NORMAL)
	void loadDroneNormal() {
		dronesService.loadDrone(DRON1, MED1);
		List<EventLog> logs = logRepo.findAll();
		assertEquals(1, logs.size());
		EventLog loadingLog = logs.get(0);
		Drone drone = loadingLog.getDrone();
		assertEquals(DRON1, drone.getNumber());
		assertEquals(State.LOADING, drone.getState());
		assertEquals(MED1, loadingLog.getMedication().getCode());
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_NOT_MATCHING_STATE)
	void loadDroneWrongState() {
		assertThrowsExactly(IllegalDroneStateException.class,
				() -> dronesService.loadDrone(DRON3, MED1));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_MEDICATION_NOT_FOUND)
	void loadDroneMedicationNotFound() {
		assertThrowsExactly(MedicationNotFoundException.class,
				() -> dronesService.loadDrone(DRON1, "KUKU"));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_NOT_FOUND)
	void loadDroneNotFound() {
		assertThrowsExactly(DroneNotFoundException.class,
				() -> dronesService.loadDrone(DRON1, "KUKU"));
	}

}
