package telran.drones.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public record LogDto(LocalDateTime timestamp, String droneNumber, State state,
		int batteryCapacity, String medicationCode) {
@Override
public String toString() {
	return String.format("%s: drone: %s, state: %s, battery capacity: %d, medication: %s",
			timestamp.format(DateTimeFormatter.ofPattern("YYYY-MM-dd HH:mm:ss")), 
			droneNumber, state, batteryCapacity, medicationCode);
}
}
