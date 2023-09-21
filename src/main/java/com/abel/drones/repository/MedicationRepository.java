package com.abel.drones.repository;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MedicationRepository extends JpaRepository<Medication,Long> {

    Optional<Medication> findMedicationByCode(String code);
}
