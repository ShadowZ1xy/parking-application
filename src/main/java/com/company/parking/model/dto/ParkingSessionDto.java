package com.company.parking.model.dto;

import com.company.parking.model.ParkingSession;
import com.company.parking.model.ParkingSlot;
import com.company.parking.service.parkingSession.ParkingSessionService;

import java.time.LocalDateTime;

public class ParkingSessionDto {
    private Long sessionId;
    private Long userId;
    private String userPublicName;
    private ParkingSlot parkingSlot;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private boolean isExpired;
    private boolean isActive;

    public ParkingSessionDto(ParkingSession parkingSession, ParkingSessionService service) {
        this.sessionId = parkingSession.getId();
        this.userId = parkingSession.getUser().getId();
        this.userPublicName = parkingSession.getUser().getPublicName();
        this.parkingSlot = parkingSession.getParkingSlot();
        this.startAt = parkingSession.getStartAt();
        this.endAt = parkingSession.getEndAt();
        this.isExpired = service.isExpired(parkingSession);
        this.isActive = service.isActive(parkingSession);
    }

    public ParkingSessionDto() {
    }


    public Long getSessionId() {
        return sessionId;
    }

    public Long getUserId() {
        return userId;
    }

    public boolean isActive() {
        return isActive;
    }

    public ParkingSlot getParkingSlot() {
        return parkingSlot;
    }

    public LocalDateTime getStartAt() {
        return startAt;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public String getUserPublicName() {
        return userPublicName;
    }
}
