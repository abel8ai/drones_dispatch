package com.abel.drones.service.impl;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.repository.PayloadItemRepository;
import com.abel.drones.repository.PayloadRepository;
import com.abel.drones.service.DroneService;
import com.abel.drones.service.PayloadService;
import com.abel.drones.service.exceptions.BadRequestException;
import com.abel.drones.service.exceptions.MedicationNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PayloadServiceImpl implements PayloadService {

    private PayloadRepository payloadRepository;
    private DroneService droneService;
    private MedicationRepository medicationRepository;
    private PayloadItemRepository payloadItemRepository;

    @Autowired
    public PayloadServiceImpl(PayloadRepository payloadRepository, DroneService droneService,
                              MedicationRepository medicationRepository,PayloadItemRepository payloadItemRepository) {
        this.payloadRepository = payloadRepository;
        this.droneService = droneService;
        this.medicationRepository = medicationRepository;
        this.payloadItemRepository = payloadItemRepository;
    }
    @Override
    public List<Payload> getAllPayloads() {
        return payloadRepository.findAll();
    }

    @Override
    public Payload createPayload(Payload payload) {
        Drone drone = droneService.getDroneById(payload.getDrone().getId());
        droneService.changeDroneState(drone.getId(), Drone.StateType.LOADING);
        // check drone availability
        if(drone.getState()!= Drone.StateType.IDLE)
            throw new BadRequestException("The selected drone is not available");
        double totalWeight = 0;
        for (PayloadItem p: payload.getPayloadItems()){
            if (!medicationRepository.existsById(p.getMedication().getId()))
                throw new MedicationNotFoundException("Medication does not exist");
            // calculate the total weight of the payload
            totalWeight+=p.getQuantity()*p.getMedication().getWeight();

        }
        // verify if the drone can carry the payload
        if (totalWeight > payload.getDrone().getWeighLimit())
            throw new BadRequestException("Weight limit exceeded");
        payload.setStatus(Payload.StatusType.ON_ROUTE);

        droneService.changeDroneState(drone.getId(), Drone.StateType.LOADED);

        // save the payload and the payload items to db
        Payload insertedPayload = payloadRepository.save(payload);

        for (PayloadItem p: payload.getPayloadItems()){
            p.setPayload(insertedPayload);
            payloadItemRepository.save(p);
        }
        return payload;

    }
}