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
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
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
        Drone drone = new Drone("qwer1234", Drone.ModelType.Cruiserweight,50,
                100);
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