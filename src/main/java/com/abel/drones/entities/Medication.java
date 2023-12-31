package com.abel.drones.entities;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private double weight;
    private String code;
    private String image;

    @OneToMany(mappedBy="medication")
    private Set<PayloadItem> payloadItems;

    public Medication() {
    }

    public Medication(String name, double weight, String code, String image) {
        this.name = name;
        this.weight = weight;
        this.code = code;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Set<PayloadItem> getPayloadItems() {
        return payloadItems;
    }

    public void setPayloadItems(Set<PayloadItem> payloadItems) {
        this.payloadItems = payloadItems;
    }
}
