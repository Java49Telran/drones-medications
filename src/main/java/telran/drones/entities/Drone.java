package telran.drones.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import telran.drones.dto.*;

@Entity
@Table(name = "drones")
@NoArgsConstructor
@Getter
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
	public void setBatteryCapacity(byte batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	public void setState(State state) {
		this.state = state;
	}
	@Enumerated(EnumType.STRING)
	@Column(nullable = false,updatable = true)
	State state;
	
	
}
