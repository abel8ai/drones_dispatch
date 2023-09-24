package com.abel.drones.repository;


import com.abel.drones.entities.Payload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayloadRepository extends JpaRepository<Payload,Long> {

}
