package telran.drones.controller;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import telran.drones.api.*;
import telran.drones.dto.DroneDto;
import telran.drones.service.DronesService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping(UrlConstants.DRONES)
@Slf4j
public class DronesController {
	final DronesService dronesService;
    @PostMapping
    DroneDto registerDrone(@RequestBody @Valid DroneDto droneDto) {
    	log.debug("received: {}", droneDto);
    	return dronesService.registerDrone(droneDto);
    }
}
