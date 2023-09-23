package com.abel.drones.service.impl;

import com.abel.drones.entities.Medication;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.service.MedicationService;
import com.abel.drones.service.exceptions.BadRequestException;
import com.abel.drones.service.exceptions.MedicationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicationServiceImpl implements MedicationService {

    private final MedicationRepository medicationRepository;

    @Autowired
    public MedicationServiceImpl(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }
    @Override
    public List<Medication> getAllMedications() {
        return medicationRepository.findAll();
    }

    @Override
    public Medication getMedicationById(Long id) {
        Optional<Medication> medOptional = medicationRepository.findById(id);
        if (!medOptional.isPresent())
            throw new MedicationNotFoundException("Incorrect ID");
        return medOptional.get();

    }

    @Override
    public Medication addMedication(Medication medication) {
        Optional<Medication> medOptional = medicationRepository.findMedicationByCode(medication.getCode());
        if (medOptional.isPresent())
            throw new BadRequestException("Code already exists");
        return medicationRepository.save(medication);
    }

    @Override
    public void removeMedicationById(Long id) {
        if (!medicationRepository.existsById(id))
            throw new MedicationNotFoundException("Incorrect ID");
        medicationRepository.deleteById(id);
    }
}