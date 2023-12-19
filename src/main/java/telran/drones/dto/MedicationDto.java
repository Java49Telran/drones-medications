package telran.drones.dto;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import static telran.drones.api.ConstraintConstants.*;
@AllArgsConstructor
@Data
public class MedicationDto {
	 
	@NotEmpty(message=MISSING_MEDICATION_CODE)
	@Pattern(regexp = MEDICATION_CODE_REGEXP)
	String code;
	@NotEmpty(message=MISSING_MEDICATION_NAME)
	@Pattern(regexp = MEDICATION_NAME_REGEXP)
	String name;
	@Max(value=MAX_WEIGHT, message=MAX_WEIGHT_VIOLATION) @Positive
	int weight;
}
