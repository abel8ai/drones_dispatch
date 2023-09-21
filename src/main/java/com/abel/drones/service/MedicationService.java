package com.abel.drones.service;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;

import java.util.List;

public interface MedicationService {

    List<Medication> getAllMedications();

    Medication getMedicationById(Long id);

    Medication AddMedication(Medication drone);

    void removeMedicationById(Long id);

}
