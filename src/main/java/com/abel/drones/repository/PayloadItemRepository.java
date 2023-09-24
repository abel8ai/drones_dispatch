package com.abel.drones.repository;

import com.abel.drones.entities.PayloadItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayloadItemRepository extends JpaRepository<PayloadItem,Long> {

}
