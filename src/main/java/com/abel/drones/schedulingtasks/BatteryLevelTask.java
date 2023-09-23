package com.abel.drones.schedulingtasks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.abel.drones.controller.DroneController;
import com.abel.drones.entities.Drone;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.service.DroneService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class BatteryLevelTask {

    @Autowired
    private DroneService droneService;
    private static final Logger log = LoggerFactory.getLogger(BatteryLevelTask.class);

    @Scheduled(fixedRate = 10000)
    public void reportBatteryLevels() {
        List<Drone> drones = droneService.getAllDrones();
        StringBuilder logData = new StringBuilder();
        for(Drone drone : drones){
            logData.append("Drone S/N: ").append(drone.getSerialNumber()).append(", Battery Level: ").append(drone.getBatteryCapacity()).append("\n");
        }
        log.info(logData.toString());
    }
}
