package com.abel.drones.service;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;

import java.util.List;

public interface DroneService {

    List<Drone> getAllDrones();

    Drone getDroneById(Long id);

    int getDroneBatteryLevelById(Long id);

    List<Drone> getIdleDrones();

    Drone registerDrone(Drone drone);

    void removeDroneById(Long id);

    List<Medication> getMedicationLoadedByDrone(Long id);
}
