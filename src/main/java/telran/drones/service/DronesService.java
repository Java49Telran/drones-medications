package telran.drones.service;

import java.util.List;

import telran.drones.dto.*;

public interface DronesService {
	DroneDto registerDrone(DroneDto droneDto);
	LogDto loadDrone(String droneNumber, String medicationCode);
	List<MedicationDto> checkMedicationItems(String droneNumber);
	List<DroneDto> checkAvailableDrones();
	int checkBatteryLevel(String droneNumber);
	List<LogDto> checkLogs(String droneNumber);
	List<DroneMedicationsAmount> checkDronesMedicationItemsAmounts();
	
}
