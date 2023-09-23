package com.abel.drones.controller;

import com.abel.drones.entities.Payload;
import com.abel.drones.service.PayloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public void cretePayload(@RequestBody Payload payload){
        payloadService.createPayload(payload);
    }
}