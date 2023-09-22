package com.abel.drones.repository;

import com.abel.drones.entities.Drone;
import com.abel.drones.entities.Medication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MedicationRepositoryTest {

    @Autowired
    private MedicationRepository medicationRepository;

    @AfterEach
    void tearDown() {
        medicationRepository.deleteAll();
    }

    @Test
    void itShouldCheckWhenMedicationCodeExists() {
        // given
        Medication medication = new Medication(1L,"med1", 4,"sdsdsd","dcfdfsdf");
        medicationRepository.save(medication);

        // when
        Optional<Medication> expected = medicationRepository.findMedicationByCode("sdsdsd");

        // then
        assertThat(expected).isPresent();
    }

    @Test
    void itShouldCheckWhenMedicationCodeNotExists() {
        // given
        // when
        Optional<Medication> expected = medicationRepository.findMedicationByCode("sdsdsd");

        // then
        assertThat(expected).isNotPresent();
    }
}