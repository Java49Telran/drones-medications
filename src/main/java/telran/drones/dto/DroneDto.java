package telran.drones.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import static telran.drones.api.ConstraintConstants.*;
@AllArgsConstructor
@Data
public class DroneDto {
	 
	@Size(max=MAX_DRONE_NUMBER_SIZE, message=DRONE_NUMBER_WRONG_LENGTH)
	@NotEmpty(message=MISSING_DRONE_NUMBER)
	String number;
	@NotNull(message=MISSING_MODEL)
	ModelType model;
	@NotNull(message=MISSING_WEIGHT_LIMIT)
	@Positive @Max(value=MAX_WEIGHT, message=MAX_WEIGHT_VIOLATION)
	Integer weightLimit; //grams 
	@Positive @Max(value=100, message = MAX_PERCENTAGE_VIOLATION) @NotNull(message = MISSING_BATTERY_CAPACITY)
	Byte batteryCapacity; //percentage
	@NotNull (message = MISSING_STATE)
	State state;
	
}
