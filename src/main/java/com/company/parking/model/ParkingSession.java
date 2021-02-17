package com.company.parking.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class ParkingSession {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @NotNull
    @ManyToOne()
    @JoinColumn(name = "parkingSlot_id")
    private ParkingSlot parkingSlot;

    @NotNull
    private LocalDateTime startAt;
    @NotNull
    private LocalDateTime endAt;


    public ParkingSession() {
    }

    public ParkingSession(User user, ParkingSlot parkingSlot, LocalDateTime startAt, LocalDateTime endAt) {
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public ParkingSession(Long id, User user, ParkingSlot parkingSlot, LocalDateTime startAt, LocalDateTime endAt) {
        this.id = id;
        this.user = user;
        this.parkingSlot = parkingSlot;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public void setParkingSlot(ParkingSlot parkingSlot) {
        this.parkingSlot = parkingSlot;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public void setStartAt(LocalDateTime startAt) {
        this.startAt = startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
    }
}
