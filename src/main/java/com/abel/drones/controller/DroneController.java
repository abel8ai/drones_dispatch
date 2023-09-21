package com.abel.drones.controller;

import com.abel.drones.entities.Drone;
import com.abel.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/drones")
public class DroneController {

    private final DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public List<Drone> getAllDrones(){
        return droneService.getAllDrones();
    }

    @GetMapping (path = "id={droneId}")
    public Drone getDroneById(@PathVariable("droneId")Long droneId){
        return droneService.getDroneById(droneId);
    }

    @PostMapping
    public void registerDrone(@RequestBody Drone drone){
        droneService.registerDrone(drone);
    }

    @DeleteMapping(path = "remove/id={droneId}")
    public void removeDroneById(@PathVariable("droneId") Long id){
        droneService.removeDroneById(id);
    }
}
