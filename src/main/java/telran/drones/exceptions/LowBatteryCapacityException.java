package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class LowBatteryCapacityException extends IllegalStateException {

	public LowBatteryCapacityException() {
		super(ServiceExceptionMessages.LOW_BATTERY_CAPACITY);
		
	}

}
