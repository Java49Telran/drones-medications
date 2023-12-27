package telran.drones;
import telran.drones.api.*;
import telran.drones.dto.DroneDto;
import telran.drones.dto.DroneMedicationsAmount;
import telran.drones.dto.LogDto;
import telran.drones.dto.MedicationDto;
import telran.drones.dto.ModelType;
import telran.drones.dto.State;
import telran.drones.entities.Drone;
import telran.drones.entities.EventLog;
import telran.drones.exceptions.DroneAlreadyExistException;
import telran.drones.exceptions.DroneNotFoundException;
import telran.drones.exceptions.IllegalDroneStateException;
import telran.drones.exceptions.MedicationNotFoundException;
import telran.drones.repo.*;
import telran.drones.service.DronesService;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
@SpringBootTest(properties = {PropertiesNames.PERIODIC_UNIT_MILLIS  + "=1000000"})
@Sql(scripts = "test_data.sql")
//('Drone-1', 'Middleweight', 300, 100, 'IDLE'),
//('Drone-2', 'Middleweight', 300, 20, 'IDLE'),
//('Drone-3', 'Middleweight', 300, 100, 'LOADING');
//('MED_1', 'Medication-1', 200),
//('MED_2', 'Medication-2', 350)	
class DronesServiceStaticTest {
	private static final String DRONE1 = "Drone-1";
	private static final String DRONE2 = "Drone-2";
	private static final String MED1 = "MED_1";
	private static final String DRONE3 = "Drone-3";
	private static final String SERVICE_TEST = "Service: ";
	private static final String DRONE4 = "Drone-4";
	private static final String MED2 = "MED_2";
	@Autowired
 DronesService dronesService;
	@Autowired
	DroneRepo droneRepo;
	@Autowired
	LogRepo logRepo;
	DroneDto droneDto = new DroneDto(DRONE4, ModelType.Cruiserweight,
			100, (byte)100, State.IDLE);
	DroneDto drone1 = new DroneDto(DRONE1, ModelType.Middleweight,
			300, (byte)100, State.IDLE);
	
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_NORMAL)
	void loadDroneNormal() {
		dronesService.loadDrone(DRONE1, MED1);
		List<EventLog> logs = logRepo.findAll();
		assertEquals(1, logs.size());
		EventLog loadingLog = logs.get(0);
		Drone drone = loadingLog.getDrone();
		assertEquals(DRONE1, drone.getNumber());
		assertEquals(State.LOADING, drone.getState());
		assertEquals(MED1, loadingLog.getMedication().getCode());
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_NOT_MATCHING_STATE)
	void loadDroneWrongState() {
		assertThrowsExactly(IllegalDroneStateException.class,
				() -> dronesService.loadDrone(DRONE3, MED1));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_MEDICATION_NOT_FOUND)
	void loadDroneMedicationNotFound() {
		assertThrowsExactly(MedicationNotFoundException.class,
				() -> dronesService.loadDrone(DRONE1, "KUKU"));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.LOAD_DRONE_NOT_FOUND)
	void loadDroneNotFound() {
		assertThrowsExactly(DroneNotFoundException.class,
				() -> dronesService.loadDrone(DRONE4, MED1));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.REGISTER_DRONE_NORMAL)
	void registerDroneNormal() {
		assertEquals(droneDto, dronesService.registerDrone(droneDto));
		assertNotNull(droneRepo.findById(DRONE4).orElse(null));
		
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.REGISTER_DRONE_ALREADY_EXISTS)
	void registerDroneAlreadyExists() {
		assertThrowsExactly(DroneAlreadyExistException.class,
				() -> dronesService.registerDrone(drone1));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_MED_ITEMS_NORMAL)
	void checkMedicationItemsNormal() {
		dronesService.loadDrone(DRONE1, MED1);
		List<MedicationDto> medications = dronesService.checkMedicationItems(DRONE1);
		assertEquals(1, medications.size());
		assertEquals(MED1, medications.get(0).getCode());
		dronesService.registerDrone(droneDto);
		List<MedicationDto> emptyMedications = dronesService.checkMedicationItems(DRONE4);
		assertTrue(emptyMedications.isEmpty());
		
		
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_MED_ITEMS_DRONE_NOT_FOUND)
	void checkMedicationItemsDroneNotFound() {
		assertThrowsExactly(DroneNotFoundException.class, () -> dronesService.checkMedicationItems(DRONE4));
		
		
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.AVAILABLE_DRONES)
	void checkAvailableDrones() {
		List<DroneDto> availableDrones = dronesService.checkAvailableDrones();
		assertEquals(1, availableDrones.size());
		assertEquals(drone1, availableDrones.get(0));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_BATTERY_LEVEL_NORMAL)
	void checkBatteryLevel() {
		assertEquals(100, dronesService.checkBatteryLevel(DRONE1));
		assertEquals(20, dronesService.checkBatteryLevel(DRONE2));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_BATTERY_LEVEL_DRONE_NOT_FOUND)
	void checkBatteryLevelDronNotFound() {
		assertThrowsExactly(DroneNotFoundException.class, ()->dronesService.checkBatteryLevel(DRONE4));
	}

	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_LOGS_NORMAL)
	void checkLogsNormal() {
		dronesService.registerDrone(droneDto);
		dronesService.loadDrone(DRONE1, MED1);
		LogDto expected = new LogDto(LocalDateTime.now(), DRONE1, State.LOADING, 100, MED1);
		List<LogDto> receivedLogs = dronesService.checkLogs(DRONE1);
		assertEquals(1, receivedLogs.size());
		assertEquals(expected, receivedLogs.get(0));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_LOGS_DRONE_NOT_FOUND)
	void checkLogsDroneNotFound() {
		assertThrowsExactly(DroneNotFoundException.class, ()->dronesService.checkLogs(DRONE4));
	}
	@Test
	@DisplayName(SERVICE_TEST + TestDisplayNames.CHECK_DRONES_ITEMS_AMOUNT)
	@Sql(scripts = {"test_data_normal_idle.sql"}) //for avoiding states mismatching
	//('Drone-1', 'Middleweight', 300, 100, 'IDLE'),
	//('Drone-2', 'Middleweight', 300, 100, 'IDLE'),
	//('Drone-3', 'Middleweight', 300, 100, 'IDLE');
	//('MED_1', 'Medication-1', 200),
	//('MED_2', 'Medication-2', 200)
	void checkDronesMedicationItemsAmounts() {
		dronesService.loadDrone(DRONE1, MED1);
		dronesService.loadDrone(DRONE2, MED2);
		Map<String, Long> resultMap =
				dronesService.checkDronesMedicationItemsAmounts().stream()
				.collect(Collectors.toMap(da -> da.getNumber(), da -> da.getAmount()));
		assertEquals(3, resultMap.size());
		assertEquals(1, resultMap.get(DRONE1));
		assertEquals(1, resultMap.get(DRONE2));
		assertEquals(0, resultMap.get(DRONE3));
		
		
	}

}
