package com.abel.drones.repository;

import com.abel.drones.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone,Long> {

    Optional<Drone> findDroneBySerialNumber(String serial);
}
