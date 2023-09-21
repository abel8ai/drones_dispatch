package com.abel.drones.controller;

import com.abel.drones.entities.Drone;
import com.abel.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/drone")
public class DroneController {

    private DroneService droneService;

    @Autowired
    public DroneController(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping
    public List<Drone> getAllDrones(){
        return droneService.getAllDrones();
    }

    @GetMapping (path = "uid={userId}")
    public Drone getUserById(@PathVariable("droneId")Long droneId){
        return droneService.getDroneById(droneId);
    }

}
