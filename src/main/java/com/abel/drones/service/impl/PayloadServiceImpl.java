package com.abel.drones.service.impl;

import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.repository.PayloadRepository;
import com.abel.drones.service.PayloadService;
import com.abel.drones.service.exceptions.DroneNotFoundException;
import com.abel.drones.service.exceptions.MedicationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new DroneNotFoundException("Drone does not exist");
        for (PayloadItem p: payload.getPayloadItems()){

            if (!medicationRepository.existsById(p.getMedication().getId()))
                throw new MedicationNotFoundException("Medication does not exist");
        }
        return payloadRepository.save(payload);
    }
}