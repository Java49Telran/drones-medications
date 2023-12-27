package telran.drones;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import telran.drones.api.PropertiesNames;
import telran.drones.dto.*;
import telran.drones.service.DronesService;

@SpringBootTest(properties = { PropertiesNames.PERIODIC_UNIT_MILLIS + "=100"})
@Sql(scripts= {"classpath:test_data_normal_idle.sql"})
class DronesServicePeriodicTaskTest {
	private static final String DRONE1 = "Drone-1";
	private static final String MED1 = "MED_1";
	@Autowired
	DronesService dronesService;

	@Test
	void test() throws InterruptedException {
		// At this step there should be 3 available drones
		availableDronesTest(3);
		dronesService.loadDrone(DRONE1, MED1);
		Thread.sleep(500);
		// At this step there should be 2 available drones
		availableDronesTest(2);
		Thread.sleep(1000);
		// At this step there should be
		// 3 available drones
		// battery capacity of Drone-1 78%
		// number of logs 12
		availableBatteryLogsTest(3, 78, 12);
		Thread.sleep(3000);
		// At this step there should be
		// 10 available drones
		// battery capacity of Drone-1 100%
		// number of logs 12 + 11 = 23
		availableBatteryLogsTest(3, 100, 23);

	}

	private void availableBatteryLogsTest(int nAvailableDrones, int batteryCapacity, int nLogs) {
		availableDronesTest(nAvailableDrones);
		batteryCapacityTest(batteryCapacity);
		logsNumberTest(nLogs);
	}

	private void logsNumberTest(int nLogs) {
		List<LogDto> logs = dronesService.checkLogs(DRONE1);
		assertEquals(nLogs, logs.size());

	}

	private void batteryCapacityTest(int capacityExpected) {
		int capacityActual = dronesService.checkBatteryLevel(DRONE1);
		assertEquals(capacityExpected, capacityActual);

	}

	private void availableDronesTest(int nDrones) {
		List<DroneDto> availableDrones = dronesService.checkAvailableDrones();
		assertEquals(nDrones, availableDrones.size());
	}

}
