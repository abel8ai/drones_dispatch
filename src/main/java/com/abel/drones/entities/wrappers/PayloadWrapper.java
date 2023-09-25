package com.abel.drones.entities.wrappers;

import java.util.List;

public class PayloadWrapper {

    private Long droneId;
    List<MedicationsWrapper> medsList;

    public PayloadWrapper(Long droneId, List<MedicationsWrapper> medsList) {
        this.droneId = droneId;
        this.medsList = medsList;
    }

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public List<MedicationsWrapper> getMedsList() {
        return medsList;
    }

    public void setMedsList(List<MedicationsWrapper> medsList) {
        this.medsList = medsList;
    }
}
