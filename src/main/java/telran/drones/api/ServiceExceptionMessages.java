package telran.drones.api;

public interface ServiceExceptionMessages {
String DRONE_NOT_FOUND = "Drone Not Found";
String MEDICATION_NOT_FOUND = "Medication Not Found";
String NOT_IDLE_STATE = "Loading may be done only in IDLE state";
String LOW_BATTERY_CAPACITY = "Too low battery capacity";
String WEIGHT_LIMIT_VIOLATION = "Dron's weight limit less than medication weight";
}
