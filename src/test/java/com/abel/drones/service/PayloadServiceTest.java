package com.abel.drones.service;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import com.abel.drones.entities.Payload;
import com.abel.drones.entities.PayloadItem;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.repository.PayloadRepository;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    private DroneRepository droneRepository;

    private PayloadService payloadService;


    @BeforeEach
    void setUp() {
        payloadService = new PayloadServiceImpl(payloadRepository,droneRepository,medicationRepository);
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
        Drone drone = new Drone(1L,"qwer1234", Drone.ModelType.Cruiserweight,50,
                100, Drone.StateType.IDLE);
        Payload payload = new Payload(1L, Payload.StatusType.ON_ROUTE, drone);
        Medication medication = new Medication(1L,"med1", 4,"sdsdsd","dcfdfsdf");
        PayloadItem payloadItem = new PayloadItem(1L,payload,medication,3);
        Set<PayloadItem> payloadItemList = new HashSet<>();
        payloadItemList.add(payloadItem);
        payload.setPayloadItems(payloadItemList);
        droneRepository.save(drone);
        medicationRepository.save(medication);

        given(droneRepository.existsById(payload.getDrone().getId()))
                .willReturn(true);

        given(medicationRepository.existsById(payload.getDrone().getId()))
                .willReturn(true);

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
    void willThrowExceptionIfTheDroneDoesntExists() {
        // given
        Drone drone = new Drone(1L,"qwer1234", Drone.ModelType.Cruiserweight,50,
                100, Drone.StateType.IDLE);
        Payload payload = new Payload(1L, Payload.StatusType.ON_ROUTE, drone);
        Medication medication = new Medication(1L,"med1", 4,"sdsdsd","dcfdfsdf");
        PayloadItem payloadItem = new PayloadItem(1L,payload,medication,3);
        Set<PayloadItem> payloadItemList = new HashSet<>();
        payloadItemList.add(payloadItem);
        payload.setPayloadItems(payloadItemList);

        given(droneRepository.existsById(payload.getDrone().getId()))
                .willReturn(false);

        // when
        // then
        assertThatThrownBy(() -> payloadService.createPayload(payload))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Drone does not exist");

        verify(payloadRepository, never()).save(any());
    }

    @Test
    void willThrowExceptionIfTheMedicationDoesntExists() {
        // given
        Drone drone = new Drone(1L,"qwer1234", Drone.ModelType.Cruiserweight,50,
                100, Drone.StateType.IDLE);
        Payload payload = new Payload(1L, Payload.StatusType.ON_ROUTE, drone);
        Medication medication = new Medication(1L,"med1", 4,"sdsdsd","dcfdfsdf");
        PayloadItem payloadItem = new PayloadItem(1L,payload,medication,3);
        Set<PayloadItem> payloadItemList = new HashSet<>();
        payloadItemList.add(payloadItem);
        payload.setPayloadItems(payloadItemList);

        given(droneRepository.existsById(payload.getDrone().getId()))
                .willReturn(true);

        for (PayloadItem p: payload.getPayloadItems()){
            given(medicationRepository.existsById(p.getMedication().getId()))
                    .willReturn(false);
        }

        // when
        // then
        assertThatThrownBy(() -> payloadService.createPayload(payload))
                .isInstanceOf(NoSuchElementException.class)
                .hasMessageContaining("Medication does not exist");

        verify(payloadRepository, never()).save(any());
    }
}