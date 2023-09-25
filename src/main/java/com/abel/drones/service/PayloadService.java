package com.abel.drones.service;

import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;

import java.util.List;

public interface PayloadService {

    Payload createPayload(Payload payload);

    List<Payload> getAllPayloads();

}
