package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;

@SuppressWarnings("serial")
public class IllegalMedicationWeightException extends IllegalStateException {

	public IllegalMedicationWeightException() {
		super(ServiceExceptionMessages.WEIGHT_LIMIT_VIOLATION);
		
	}

}
