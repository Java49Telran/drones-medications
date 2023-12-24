package telran.drones;

public interface TestDisplayNames {
String REGISTER_DRONE_NORMAL = "Registering drone normal flow";
String REGISTER_DRONE_VALIDATION_VIOLATION = "Drone JSON with  wrong fields";
String REGISTER_DRONE_MISSING_FIELDS = "Drone JSON with missing fields";
String REGISTER_DRONE_ALREADY_EXISTS = "Registering Drone with existing number";
String LOAD_DRONE_NORMAL = "Loading Drone normal flow";
String LOAD_DRONE_NOT_FOUND = "Loading Drone Not Found";
String LOAD_DRONE_MEDICATION_NOT_FOUND = "Loading Drone Medication Not Found";
String LOAD_DRONE_LOW_BATTERY_CAPCITY = "Loading Drone Low Battery Capacity";
String LOAD_DRONE_NOT_MATCHING_WEIGHT = "Loading Drone Not Matching Weight";
String LOAD_DRONE_NOT_MATCHING_STATE = "Loading Drone State not IDLE";
String LOAD_DRONE_WRONG_FIELDS = "Loading Drone Wrong Fields";
String LOAD_DRONE_MISSING_FIELDS = "Loading Drone Missing Fields";
String CHECK_MED_ITEMS_NORMAL = "checking Medication Items Normal Flow";
String CHECK_MED_ITEMS_DRONE_NOT_FOUND = "checking Medication Items, Drone Not Found";
String AVAILABLE_DRONES = "checking available for loading drones";
String CHECK_BATTERY_LEVEL_NORMAL = "checking battery level, normal flow";
String CHECK_BATTERY_LEVEL_DRONE_NOT_FOUND = "checking battery level, drone not found";
String CHECK_LOGS_NORMAL = "Checking Logs Normal Flow";
String CHECK_LOGS_DRONE_NOT_FOUND = "Checking Logs, Drone Not Found";
String CHECK_DRONES_ITEMS_AMOUNT = "Checking Drone Numbers and amounts of the loaded medication items";
String REGISTER_DRONE_WRONG_TYPE = "Registring Drone with JSON containing mismatch fields";

}
