package telran.drones.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.drones.entities.*;

public interface MedicationRepo extends JpaRepository<Medication, String> {

}
