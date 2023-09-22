package com.abel.drones.repository;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PayloadRepository extends JpaRepository<Payload,Long> {

}
