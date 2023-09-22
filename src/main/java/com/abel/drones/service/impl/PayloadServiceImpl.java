package com.abel.drones.service.impl;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.repository.PayloadRepository;
import com.abel.drones.service.PayloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class PayloadServiceImpl implements PayloadService {

    private PayloadRepository payloadRepository;
    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;

    @Autowired
    public PayloadServiceImpl(PayloadRepository payloadRepository, DroneRepository droneRepository,
                              MedicationRepository medicationRepository) {
        this.payloadRepository = payloadRepository;
        this.droneRepository = droneRepository;
        this.medicationRepository = medicationRepository;
    }
    @Override
    public List<Payload> getAllPayloads() {
        return payloadRepository.findAll();
    }

    @Override
    public Payload createPayload(Payload payload) {

        if (!droneRepository.existsById(payload.getDrone().getId()))
            throw new NoSuchElementException("Drone does not exist");
        for (PayloadItem p: payload.getPayloadItems()){

            if (!medicationRepository.existsById(p.getMedication().getId()))
                throw new NoSuchElementException("Medication does not exist");
        }
        return payloadRepository.save(payload);
    }
}