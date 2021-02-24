package com.company.parking.model.dto;

import com.company.parking.model.ParkingSession;
import com.company.parking.model.User;

import java.time.LocalDateTime;

public class ParkingTicket {
    private Long ticketId;

    private String userPublicName;
    private Long userId;
    private Long slotId;
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    public ParkingTicket() {
    }

    public ParkingTicket(ParkingSession session, Long slotId, User user) {
        ParkingTicket ticket = new ParkingTicket();
        ticket.setTicketId(session.getId());
        ticket.setSlotId(slotId);
        ticket.setUserId(user.getId());
        ticket.setUserPublicName(user.getPublicName());
        ticket.setStartAt(session.getStartAt());
        ticket.setEndAt(session.getEndAt());
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getSlotId() {
        return slotId;
    }

    public void setSlotId(Long slotId) {
        this.slotId = slotId;
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

    public String getUserPublicName() {
        return userPublicName;
    }

    public void setUserPublicName(String userPublicName) {
        this.userPublicName = userPublicName;
    }
}
