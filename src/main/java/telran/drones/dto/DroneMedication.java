package telran.drones.dto;
import jakarta.validation.constraints.*;
import static telran.drones.api.ConstraintConstants.*;
public record DroneMedication(@NotEmpty @Size(max=MAX_DRONE_NUMBER_SIZE)String droneNumber,
		@Pattern(regexp = MEDICATION_CODE_REGEXP) String medicationCode) {

}
