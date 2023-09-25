package com.abel.drones.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Drone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "serial_number", nullable = false, length = 100)
    private String serialNumber;

    public enum ModelType {Lightweight, Middleweight, Cruiserweight, Heavyweight}
    @Enumerated(EnumType.STRING)
    private ModelType model;

    @Column(name = "weigh_limit", nullable = false)
    private double weighLimit;

    @Column(name = "battery_capacity", nullable = false)
    private int batteryCapacity;

    public enum StateType {IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING}
    @Enumerated(EnumType.STRING)
    private StateType state;

    @OneToMany(mappedBy="drone")
    private Set<Payload> payloads;

    public Drone() {

    }

    public Drone(String serialNumber, ModelType model, double weighLimit, int batteryCapacity) {
        this.serialNumber = serialNumber;
        this.model = model;
        this.weighLimit = weighLimit;
        this.batteryCapacity = batteryCapacity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public ModelType getModel() {
        return model;
    }

    public void setModel(ModelType model) {
        this.model = model;
    }

    public double getWeighLimit() {
        return weighLimit;
    }

    public void setWeighLimit(double weighLimit) {
        this.weighLimit = weighLimit;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public StateType getState() {
        return state;
    }

    public void setState(StateType state) {
        this.state = state;
    }

    public Set<Payload> getPayloads() {
        return payloads;
    }

    public void setPayloads(Set<Payload> payloads) {
        this.payloads = payloads;
    }
}
