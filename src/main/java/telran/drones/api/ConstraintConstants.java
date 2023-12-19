package telran.drones.api;

public interface ConstraintConstants {
int MAX_DRONE_NUMBER_SIZE = 100;
int MAX_WEIGHT = 500;
String DRONE_NUMBER_WRONG_LENGTH = "wrong drone number (incorrect length)";
String MISSING_DRONE_NUMBER = "missing drone number";
String MISSING_MODEL = "missing model";
String MISSING_WEIGHT_LIMIT = "missing weight limit";
String MISSING_BATTERY_CAPACITY = "missing battery capacity";
String MISSING_STATE = "missing state";
String MISSING_MEDICATION_CODE = "missing Medication Code";
String MEDICATION_CODE_REGEXP = "[A-Z_\\d]+";
String MISSING_MEDICATION_NAME = "missing Medication Name";
String MEDICATION_NAME_REGEXP = "[\\w-]+";
String MAX_WEIGHT_VIOLATION = "Weight cannot be greater than " + MAX_WEIGHT;
String MAX_PERCENTAGE_VIOLATION = "Percent value cannot be greater than 100";
}
