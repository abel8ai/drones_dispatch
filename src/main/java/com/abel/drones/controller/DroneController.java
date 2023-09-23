package com.abel.drones.controller;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v2/drones")
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

    @GetMapping (path = "{droneId}")
    public Drone getDroneById(@PathVariable("droneId")Long droneId){
        return droneService.getDroneById(droneId);
    }

    @GetMapping(path = "{droneId}/battery-level")
    public int getDroneBatteryLevelById(@PathVariable("droneId")Long droneId){
        return droneService.getDroneBatteryLevelById(droneId);
    }

    @GetMapping(path = "{droneId}/loaded-meds")
    public List<Medication> getMedicationLoadedByDrone(@PathVariable("droneId")Long droneId){
        return droneService.getMedicationLoadedByDrone(droneId);
    }

    @GetMapping(path = "idle")
    public List<Drone> getIdleDrones(){
        return droneService.getIdleDrones();
    }

    @PostMapping(path = "create")
    public void registerDrone(@RequestBody Drone drone){
        droneService.registerDrone(drone);
    }

    @DeleteMapping(path = "{droneId}/remove")
    public void removeDroneById(@PathVariable("droneId") Long id){
        droneService.removeDroneById(id);
    }

    @PutMapping(path = "{droneId}/change-state")
    public void changeDroneState(@PathVariable("droneId") Long droneId,
                                    @RequestParam Drone.StateType state){
        droneService.changeDroneState(droneId,state);
    }
}
