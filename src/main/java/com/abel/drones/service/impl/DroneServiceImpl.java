package com.abel.drones.service.impl;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.service.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class DroneServiceImpl implements DroneService {

    private DroneRepository droneRepository;

    @Autowired
    public DroneServiceImpl(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }
    @Override
    public List<Drone> getAllDrones() {
        return droneRepository.findAll();
    }

    @Override
    public Drone getDroneById(Long id) {
        Optional<Drone> droneOptional = droneRepository.findById(id);
        if (!droneOptional.isPresent())
            throw new NoSuchElementException("Incorrect ID");
        return droneOptional.get();

    }

    @Override
    public int getDroneBatteryLevelById(Long id) {
        return getDroneById(id).getBatteryCapacity();
    }

    @Override
    public List<Drone> getIdleDrones() {
        return droneRepository.findDroneByStateEquals(Drone.StateType.IDLE).get();
    }

    @Override
    public Drone registerDrone(Drone drone) {
        Optional<Drone> droneOptional = droneRepository.findDroneBySerialNumber(drone.getSerialNumber());
        if (droneOptional.isPresent())
            throw new IllegalStateException("Serial number already exists");
        return droneRepository.save(drone);
    }

    @Override
    public void removeDroneById(Long id) {
        if (!droneRepository.existsById(id))
            throw new NoSuchElementException("Incorrect ID");
        droneRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void changeDroneState(Long id, Drone.StateType state) {

        if (!droneRepository.existsById(id))
            throw new NoSuchElementException("Incorrect ID");
        else {
            Optional<Drone> droneOptional = droneRepository.findById(id);
            droneOptional.ifPresent(drone -> drone.setState(state));
        }
    }

    @Override
    public List<Medication> getMedicationLoadedByDrone(Long id) {
        List<Medication> medications = new ArrayList<>();
        Optional<Drone> droneOptional = droneRepository.findById(id);
        if (!droneOptional.isPresent())
            throw new NoSuchElementException("Incorrect ID");
        else {
            Drone drone = droneOptional.get();
            Optional<Payload> payloadOptional = drone.getPayloads().stream()
                    .filter(p -> p.getStatus() == Payload.StatusType.ON_ROUTE).findFirst();
            if (!payloadOptional.isPresent())
                return medications;
            else {
                for (PayloadItem p : payloadOptional.get().getPayloadItems()){
                    medications.add(p.getMedication());
                }
            }


            return medications;
        }
    }


}
