package com.abel.drones.service;

import com.abel.drones.entities.Drone;
import com.abel.drones.repository.DroneRepository;
import com.abel.drones.service.exceptions.BadRequestException;
import com.abel.drones.service.exceptions.DroneNotFoundException;
import com.abel.drones.service.impl.DroneServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DroneServiceTest {

    @Mock
    private DroneRepository droneRepository;

    private DroneServiceImpl droneService;

    @BeforeEach
    public void setUp(){
        droneService = new DroneServiceImpl(droneRepository);
    }

    @Test
    void canGetAllDrones() {
        // when
        droneService.getAllDrones();
        // then
        verify(droneRepository).findAll();
    }

    @Test
    void canRegisterDrone() {
        // given
        Drone drone = new Drone("qwer1234", Drone.ModelType.Cruiserweight,50,
                100);

        // when
        droneService.registerDrone(drone);

        // then
        ArgumentCaptor<Drone> droneArgumentCaptor =
                ArgumentCaptor.forClass(Drone.class);

        verify(droneRepository)
                .save(droneArgumentCaptor.capture());

        Drone capturedDrone = droneArgumentCaptor.getValue();

        assertThat(capturedDrone).isEqualTo(drone);
    }

    @Test
    void willThrowExceptionWhenSerialNumberExists() {
        // given
        Drone drone = new Drone("qwer1234", Drone.ModelType.Cruiserweight,50,
                100);

        given(droneRepository.findDroneBySerialNumber(anyString()))
                .willReturn(java.util.Optional.of(drone));

        // when
        // then
        assertThatThrownBy(() -> droneService.registerDrone(drone))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Serial number already exists");

        verify(droneRepository, never()).save(any());

    }

    @Test
    void canRemoveDrone() {
        // given
        long id = 2;
        given(droneRepository.existsById(id))
                .willReturn(true);
        // when
        droneService.removeDroneById(id);

        // then
        verify(droneRepository).deleteById(id);
    }

    @Test
    void willThrowExceptionWhenRemoveDroneNotFound() {
        // given
        long id = 10;
        given(droneRepository.existsById(id))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> droneService.removeDroneById(id))
                .isInstanceOf(DroneNotFoundException.class)
                .hasMessageContaining("Incorrect ID");

        verify(droneRepository, never()).deleteById(any());
    }

    @Test
    void willThrowExceptionWhenUpdateDroneNotFound() {
        // given
        long id = 10;
        given(droneRepository.existsById(id))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> droneService.changeDroneState(id, Drone.StateType.IDLE))
                .isInstanceOf(DroneNotFoundException.class)
                .hasMessageContaining("Incorrect ID");

        verify(droneRepository, never()).save(any());
    }

    @Test
    void canChangeDroneState() {
        // given
        Drone drone = new Drone("qwer1234", Drone.ModelType.Cruiserweight,50,
                100);
        droneRepository.save(drone);
        long id = 2;
        given(droneRepository.existsById(id))
                .willReturn(true);
        // when
        droneService.changeDroneState(id, Drone.StateType.IDLE);

        // then
        ArgumentCaptor<Drone> droneArgumentCaptor =
                ArgumentCaptor.forClass(Drone.class);

        verify(droneRepository)
                .save(droneArgumentCaptor.capture());

        Drone capturedDrone = droneArgumentCaptor.getValue();

        assertThat(capturedDrone).isEqualTo(drone);
    }
}