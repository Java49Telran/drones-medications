package telran.drones.entities;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;
import telran.drones.dto.LogDto;
import telran.drones.dto.State;
@Entity
@RequiredArgsConstructor
@NoArgsConstructor
@Table(name="logs")
@Getter
public class EventLog {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
long id;
@ManyToOne
@JoinColumn(name="drone_number", nullable = false, updatable = false)
@NonNull Drone drone;
@ManyToOne
@JoinColumn(name="medication_code", nullable = false, updatable = false)
@NonNull Medication medication;
@Temporal(TemporalType.TIMESTAMP)
@Column(nullable = false, updatable = false)
@NonNull LocalDateTime timestamp;
@NonNull State state;
@Column(name="battery_capacity")
@NonNull Byte batteryCapacity;
public LogDto build() {
	return new LogDto(timestamp, drone.number, state, batteryCapacity, medication.code);
}

}
