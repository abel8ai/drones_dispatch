package com.abel.drones.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Payload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private StatusType status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="drone_id", nullable=false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Drone drone;

    @OneToMany(mappedBy="payload")
    private Set<PayloadItem> payloadItems;

    public enum StatusType{UNASSIGNED, ON_ROUTE,COMPLETED}

    public Payload(Long id, StatusType status, Drone drone) {
        this.id = id;
        this.status = status;
        this.drone = drone;
    }

    public Payload() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Set<PayloadItem> getPayloadItems() {
        return payloadItems;
    }

    public void setPayloadItems(Set<PayloadItem> payloadItems) {
        this.payloadItems = payloadItems;
    }
}
