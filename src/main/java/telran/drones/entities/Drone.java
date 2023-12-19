package telran.drones.entities;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import telran.drones.dto.*;

@Entity
@Table(name = "drones")
@NoArgsConstructor
public class Drone {
	@Id
	@Column(length = 100)
	String number;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,updatable = false)
	ModelType model;
	@Column(nullable = false,updatable = false, name="weight_limit")
	int weightLimit;
	@Column(nullable = false,updatable = true, name="battery_capacity")
	byte batteryCapacity;
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,updatable = true)
	State state;
	
	
}
