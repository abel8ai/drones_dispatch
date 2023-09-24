package com.abel.drones.service.impl;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.service.DroneService;
import com.abel.drones.service.exceptions.BadRequestException;
import com.abel.drones.service.exceptions.DroneNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
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
            throw new DroneNotFoundException("Incorrect ID");
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
            throw new BadRequestException("Serial number already exists");
        if (!isValidData(drone))
            throw new BadRequestException("Invalid drone data");
        drone.setState(Drone.StateType.IDLE);
        return droneRepository.save(drone);
    }

    @Override
    public void removeDroneById(Long id) {
        if (!droneRepository.existsById(id))
            throw new DroneNotFoundException("Incorrect ID");
        droneRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void changeDroneState(Long id, Drone.StateType state) {

        if (!droneRepository.existsById(id))
            throw new DroneNotFoundException("Incorrect ID");
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
            throw new DroneNotFoundException("Incorrect ID");
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
    public boolean isValidData(Drone drone){
        if (drone.getSerialNumber().length()>100)
            return false;
        if (drone.getWeighLimit()>500)
            return false;
        if (drone.getBatteryCapacity()>100)
            return false;

        return true;
    }


}
