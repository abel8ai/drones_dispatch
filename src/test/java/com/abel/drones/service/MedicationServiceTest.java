package com.abel.drones.service;

import com.abel.drones.entities.Medication;
import com.abel.drones.repository.MedicationRepository;
import com.abel.drones.service.exceptions.BadRequestException;
import com.abel.drones.service.exceptions.MedicationNotFoundException;
import com.abel.drones.service.impl.MedicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)

class MedicationServiceTest {

    @Mock
    private MedicationRepository medicationRepository;

    private MedicationService medicationService;

    @BeforeEach
    public void setUp(){
        medicationService = new MedicationServiceImpl(medicationRepository);
    }

    @Test
    void canGetAllMedications() {
        // when
        medicationService.getAllMedications();
        // then
        verify(medicationRepository).findAll();
    }

    @Test
    void canAddMedication() {
        // given
        Medication medication = new Medication(1L,"med1", 4,"sdsdsd","dcfdfsdf");

        // when
        medicationService.addMedication(medication);

        // then
        ArgumentCaptor<Medication> medArgumentCaptor =
                ArgumentCaptor.forClass(Medication.class);

        verify(medicationRepository)
                .save(medArgumentCaptor.capture());

        Medication capturedMed = medArgumentCaptor.getValue();

        assertThat(capturedMed).isEqualTo(medication);
    }

    @Test
    void willThrowWhenCodeExists() {
        // given
        Medication medication = new Medication(1L,"med1", 4,"sdsdsd","dcfdfsdf");

        given(medicationRepository.findMedicationByCode(anyString()))
                .willReturn(java.util.Optional.of(medication));

        // when
        // then
        assertThatThrownBy(() -> medicationService.addMedication(medication))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("Code already exists");

        verify(medicationRepository, never()).save(any());

    }

    @Test
    void canRemoveMedication() {
        // given
        long id = 2;
        given(medicationRepository.existsById(id))
                .willReturn(true);
        // when
        medicationService.removeMedicationById(id);

        // then
        verify(medicationRepository).deleteById(id);
    }

    @Test
    void willThrowWhenRemoveMedicationNotFound() {
        // given
        long id = 10;
        given(medicationRepository.existsById(id))
                .willReturn(false);
        // when
        // then
        assertThatThrownBy(() -> medicationService.removeMedicationById(id))
                .isInstanceOf(MedicationNotFoundException.class)
                .hasMessageContaining("Incorrect ID");

        verify(medicationRepository, never()).deleteById(any());
    }
}