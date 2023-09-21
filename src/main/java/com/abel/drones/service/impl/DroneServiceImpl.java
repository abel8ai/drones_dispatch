package com.abel.drones.service.impl;

import com.abel.drones.entities.Drone;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DroneServiceImpl implements DroneService {

    private DroneRepository droneRepository;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }
    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public Drone getDroneById(Long id) {
        return null;
    }

    @Override
    public Drone registerDrone(Drone drone) {
        return null;
    }

    @Override
    public void removeDroneById(Long id) {

    }
}
