package telran.drones.exceptions;

import telran.drones.api.ServiceExceptionMessages;
import telran.exceptions.NotFoundException;

@SuppressWarnings("serial")
public class MedicationNotFoundException extends NotFoundException {

	public MedicationNotFoundException() {
		super(ServiceExceptionMessages.MEDICATION_NOT_FOUND);
		
	}

}
