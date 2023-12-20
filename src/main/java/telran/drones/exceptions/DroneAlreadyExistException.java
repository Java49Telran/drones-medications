package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class DroneAlreadyExistException extends IllegalStateException {

	public DroneAlreadyExistException() {
		super(ServiceExceptionMessages.DRONE_ALREADY_EXISTS);
		
	}

}
