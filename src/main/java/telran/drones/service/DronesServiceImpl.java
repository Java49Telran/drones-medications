package telran.drones.service;

import java.time.LocalDateTime;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import telran.drones.api.PropertiesNames;
import telran.drones.dto.*;
import telran.drones.entities.*;
import telran.drones.exceptions.DroneAlreadyExistException;
import telran.drones.exceptions.DroneNotFoundException;
import telran.drones.exceptions.IllegalDroneStateException;
import telran.drones.exceptions.IllegalMedicationWeightException;
import telran.drones.exceptions.LowBatteryCapacityException;
import telran.drones.exceptions.MedicationNotFoundException;
import telran.drones.repo.*;
@Service
@RequiredArgsConstructor
public class DronesServiceImpl implements DronesService {
final DroneRepo droneRepo;
final MedicationRepo medicationRepo;
final LogRepo logRepo;
final ModelMapper modelMapper;
@Value("${" + PropertiesNames.CAPACITY_THRESHOLD + ":25}")
byte capacityThreshold;

	@Override
	@Transactional
	public DroneDto registerDrone(DroneDto droneDto) {
		if(droneRepo.existsById(droneDto.getNumber())) {
			throw new DroneAlreadyExistException();
		}
		Drone drone = modelMapper.map(droneDto, Drone.class);
		drone.setState(State.IDLE);
		droneRepo.save(drone);
		return droneDto;
	}

	@Override
	@Transactional(readOnly = false)
	public LogDto loadDrone(String droneNumber, String medicationCode) {
		Drone drone = droneRepo.findById(droneNumber)
				.orElseThrow(() -> new DroneNotFoundException());
		Medication medication = medicationRepo.findById(medicationCode)
				.orElseThrow(() -> new MedicationNotFoundException());
		if(drone.getState() != State.IDLE) {
			throw new IllegalDroneStateException();
		}
		
		if(drone.getBatteryCapacity() < capacityThreshold) {
			throw new LowBatteryCapacityException();
		}
		if(drone.getWeightLimit() < medication.getWeight()) {
			throw new IllegalMedicationWeightException();
		}
		drone.setState(State.LOADING);
		EventLog log = new EventLog(drone, medication, LocalDateTime.now());
		logRepo.save(log);
		LogDto res = new LogDto(log.getTimestamp(), droneNumber,
				drone.getState(), drone.getBatteryCapacity(), medicationCode);
		return res;
	}

	@Override
	public List<MedicationDto> checkMedicationItems(String droneNumber) {
		// TODO Auto-generated method stub
		return null;
	}

}
