package com.abel.drones.repository;

import com.abel.drones.entities.Drone;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class DroneRepositoryTest {

    @Autowired
    private DroneRepository droneRepository;

    @AfterEach
    void tearDown() {
        droneRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenDroneSerialNumberExists() {
        // given
        Drone drone = new Drone(1L,"qwer1234", Drone.ModelType.Cruiserweight,50,
                100, Drone.StateType.IDLE);
        droneRepository.save(drone);

        // when
        Optional<Drone> expected = droneRepository.findDroneBySerialNumber("qwer1234");

        // then
        assertThat(expected).isPresent();
    }

    @Test
    void itShouldCheckWhenDroneSerialNumberDoesNotExists() {
        // given
        // when
        Optional<Drone> expected = droneRepository.findDroneBySerialNumber("erty2345");

        // then
        assertThat(expected).isNotPresent();
    }
}