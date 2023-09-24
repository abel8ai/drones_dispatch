package com.abel.drones.service;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.repository.PayloadItemRepository;
import com.abel.drones.repository.PayloadRepository;
import com.abel.drones.service.exceptions.DroneNotFoundException;
import com.abel.drones.service.exceptions.MedicationNotFoundException;
import com.abel.drones.service.impl.PayloadServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PayloadServiceTest {

    @Mock
    private PayloadRepository payloadRepository;
    @Mock
    private MedicationRepository medicationRepository;
    @Mock
    private DroneService droneService;
    @Mock
    private PayloadItemRepository payloadItemRepository;

    private PayloadService payloadService;


    @BeforeEach
    void setUp() {
        payloadService = new PayloadServiceImpl(payloadRepository,droneService,medicationRepository, payloadItemRepository);
    }


    @Test
    void canGetAllPayloads() {
        // when
        payloadService.getAllPayloads();
        // then
        verify(payloadRepository).findAll();
    }
    @Test
    void canCreatePayload() {
        // given
        Drone drone = new Drone("qwer1234", Drone.ModelType.Cruiserweight,50,
                100);
        Payload payload = new Payload(1L, Payload.StatusType.ON_ROUTE, drone);
        Medication medication = new Medication("med1", 4,"JHKJHK","dcfdfsdf");
        PayloadItem payloadItem = new PayloadItem(1L,payload,medication,3);
        Set<PayloadItem> payloadItemList = new HashSet<>();
        payloadItemList.add(payloadItem);
        payload.setPayloadItems(payloadItemList);
        medicationRepository.save(medication);

        given(droneService.getDroneById(payload.getDrone().getId()))
                .willReturn(drone);

        for (PayloadItem p: payload.getPayloadItems()){
            given(medicationRepository.existsById(p.getMedication().getId()))
                    .willReturn(true);
        }

        // when
        payloadService.createPayload(payload);

        // then
        ArgumentCaptor<Payload> payloadArgumentCaptor =
                ArgumentCaptor.forClass(Payload.class);

        verify(payloadRepository)
                .save(payloadArgumentCaptor.capture());

        Payload capturedPayload = payloadArgumentCaptor.getValue();

        assertThat(capturedPayload).isEqualTo(payload);
    }

    @Test
    void willThrowExceptionIfTheMedicationDoesntExists() {
        // given
        Drone drone = new Drone("qwer1234", Drone.ModelType.Cruiserweight,50,
                100);
        Payload payload = new Payload(1L, Payload.StatusType.ON_ROUTE, drone);
        Medication medication = new Medication("med1", 4,"HJKJHK","dcfdfsdf");
        PayloadItem payloadItem = new PayloadItem(1L,payload,medication,3);
        Set<PayloadItem> payloadItemList = new HashSet<>();
        payloadItemList.add(payloadItem);
        payload.setPayloadItems(payloadItemList);

        given(droneService.getDroneById(payload.getDrone().getId()))
                .willReturn(drone);

        for (PayloadItem p: payload.getPayloadItems()){
            given(medicationRepository.existsById(p.getMedication().getId()))
                    .willReturn(false);
        }

        // when
        // then
        assertThatThrownBy(() -> payloadService.createPayload(payload))
                .isInstanceOf(MedicationNotFoundException.class)
                .hasMessageContaining("Medication does not exist");

        verify(payloadRepository, never()).save(any());
    }
}