package telran.drones.api;

public interface UrlConstants {
String DRONES = "drones";
String LOAD_DRONE = DRONES + "/load"; 
String GET_DRONES_AVAILABLE = DRONES + "/available";
String GET_DRONE_MEDICATIONS = DRONES + "/medications/"; 
String GET_DRONE_BATTERY_CAPACITY = DRONES + "/battery/capacity/";
String GET_DRONE_LOGS = DRONES + "/logs/";
String GET_DRONES_MEDICATIONS_AMOUNTS = DRONES + "/items/amounts";
String DRONE_NUMBER_IN_PATH = "droneNumber";
}
