package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class IllegalDroneStateException extends IllegalStateException {

	public IllegalDroneStateException() {
		super(ServiceExceptionMessages.NOT_IDLE_STATE);
		
	}

}
