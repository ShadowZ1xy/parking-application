package com.company.parking.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ParkingSlot {
    @Id
    @GeneratedValue
    @Column(name = "parkingSlot_id")
    private Long id;
    private String info;

    public ParkingSlot(Long id, String info) {
        this.id = id;
        this.info = info;
    }

    public ParkingSlot() {
    }

    public ParkingSlot(Long id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
