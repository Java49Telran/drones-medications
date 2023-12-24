package telran.drones.dto;
import jakarta.validation.constraints.*;
import static telran.drones.api.ConstraintConstants.*;
public record DroneMedication(@NotEmpty(message=MISSING_DRONE_NUMBER) @Size(max=MAX_DRONE_NUMBER_SIZE,
message=DRONE_NUMBER_WRONG_LENGTH)String droneNumber,
		@NotEmpty(message=MISSING_MEDICATION_CODE) @Pattern(regexp = MEDICATION_CODE_REGEXP, message=WRONG_MEDICATION_CODE_MESSAGE) String medicationCode) {

}
