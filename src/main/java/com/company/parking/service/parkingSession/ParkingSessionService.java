package com.company.parking.service.parkingSession;

import com.company.parking.model.ParkingSession;
import com.company.parking.model.dto.ParkingRequest;
import com.company.parking.model.dto.ParkingSessionDto;
import com.company.parking.model.dto.ParkingTicket;

import java.time.LocalDateTime;

public interface ParkingSessionService {
    Iterable<ParkingSessionDto> getAllAndMapItToDto();

    ParkingTicket createNew(ParkingRequest request);

    ParkingTicket createNew(LocalDateTime start, LocalDateTime end);

    ParkingSessionDto getById(Long id);

    Iterable<ParkingSessionDto> getAllNonExpired();

    boolean isExpired(ParkingSession session);

    boolean isActive(ParkingSession parkingSession);

    Iterable<ParkingSessionDto> getAllActive();

    void deleteUserSession();
}
