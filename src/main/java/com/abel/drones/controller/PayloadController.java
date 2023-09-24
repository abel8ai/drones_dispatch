package com.abel.drones.controller;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.entities.wrappers.MedicationsWrapper;
import com.abel.drones.entities.wrappers.PayloadWrapper;
import com.abel.drones.service.PayloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping(path = "api/v2/payloads")
public class PayloadController {

    private final PayloadService payloadService;

    @Autowired
    public PayloadController(PayloadService payloadService) {
        this.payloadService = payloadService;
    }

    @GetMapping
    public List<Payload> getAllPayloads(){
        return payloadService.getAllPayloads();
    }


    @PostMapping(path = "create")
    public void cretePayload(@RequestBody PayloadWrapper payloadWrapper){

        Drone drone = new Drone();
        drone.setId(payloadWrapper.getDroneId());
        Set<PayloadItem> payloadItemSet = new HashSet<>();
        for(MedicationsWrapper med: payloadWrapper.getMedsList()){
            Medication medication = new Medication();
            medication.setId(med.getMedicationId());
            PayloadItem item = new PayloadItem(null,medication,med.getQuantity());
            payloadItemSet.add(item);
        }

        Payload payload = new Payload(null, Payload.StatusType.UNASSIGNED,drone);
        payload.setPayloadItems(payloadItemSet);

        payloadService.createPayload(payload);
    }
}