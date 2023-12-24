package telran.drones.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import telran.drones.api.*;
import telran.drones.dto.*;
import telran.drones.service.DronesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DronesController {
	final DronesService dronesService;
    @PostMapping(UrlConstants.DRONES)
    DroneDto registerDrone(@RequestBody @Valid DroneDto droneDto) {
    	log.debug("received: {}", droneDto);
    	return dronesService.registerDrone(droneDto);
    }
    @PostMapping(UrlConstants.LOAD_DRONE)
    LogDto loadDrone(@RequestBody @Valid DroneMedication droneMedication) {
    	log.debug("received: {}", droneMedication);
    	return dronesService.loadDrone(droneMedication.droneNumber(), droneMedication.medicationCode());
    }
    @GetMapping(UrlConstants.GET_DRONES_AVAILABLE)
    List<DroneDto> getAvailableDrones() {
    	return dronesService.checkAvailableDrones();
    }
    @GetMapping(UrlConstants.GET_DRONE_MEDICATIONS + "{droneNumber}")
    List<MedicationDto> getDroneMedications(@PathVariable String droneNumber) {
    	log.debug("received: drone number {}", droneNumber );
    	return dronesService.checkMedicationItems(droneNumber);
    }
    @GetMapping(UrlConstants.GET_DRONE_BATTERY_CAPACITY + "{droneNumber}")
    int getDroneBatteryCapacity(@PathVariable String droneNumber) {
    	log.debug("received: drone number {}", droneNumber );
    	return dronesService.checkBatteryLevel(droneNumber);
    }
    @GetMapping(UrlConstants.GET_DRONE_LOGS + "{droneNumber}")
    List<LogDto> getDroneLogs(@PathVariable String droneNumber) {
    	log.debug("received: drone number {}", droneNumber );
    	return dronesService.checkLogs(droneNumber);
    }
    @GetMapping(UrlConstants.GET_DRONES_MEDICATIONS_AMOUNTS)
    List<DroneMedicationsAmount> getDronesMedicationsAmounts() {
    	return dronesService.checkDronesMedicationItemsAmounts();
    }
    
}
