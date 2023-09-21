package com.abel.drones.service;

import com.abel.drones.entities.Drone;

import java.util.List;

public interface DroneService {

    List<Drone> getAllDrones();

    Drone getDroneById(Long id);

    Drone registerDrone(Drone drone);

    void removeDroneById(Long id);
}
