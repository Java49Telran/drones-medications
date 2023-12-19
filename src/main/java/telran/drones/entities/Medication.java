package telran.drones.entities;
import jakarta.persistence.*;
@Entity
@Table(name="medications")
public class Medication {
	@Id
	String code;
	@Column(nullable = false)
	String name;
	@Column(nullable = false)
	int weight;
}
