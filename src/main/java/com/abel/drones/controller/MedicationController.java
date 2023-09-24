package com.abel.drones.controller;

import com.abel.drones.entities.Medication;
import com.abel.drones.service.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v2/medications")
public class MedicationController {

    private final MedicationService medicationService;

    @Autowired
    public MedicationController(MedicationService medicationService) {
        this.medicationService = medicationService;
    }

    @GetMapping
    public List<Medication> getAllMedication(){
        return medicationService.getAllMedications();
    }

    @GetMapping (path = "{medId}")
    public Medication getMedicationById(@PathVariable("medId")Long medId){
        return medicationService.getMedicationById(medId);
    }

    @PostMapping(path = "create")
    public void addMedication(@RequestBody Medication medication){
        medicationService.addMedication(medication);
    }

    @DeleteMapping(path = "{medId}/remove")
    public void removeMedicationById(@PathVariable("medId") Long id){
        medicationService.removeMedicationById(id);
    }
}