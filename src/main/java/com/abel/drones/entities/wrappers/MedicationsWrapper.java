package com.abel.drones.entities.wrappers;

public class MedicationsWrapper {
    private Long medicationId;
    private int quantity;

    public MedicationsWrapper(Long medId, int quantity) {
        this.medicationId = medId;
        this.quantity = quantity;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
