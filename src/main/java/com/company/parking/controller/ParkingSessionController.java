package com.company.parking.controller;

import com.company.parking.model.dto.ParkingRequest;
import com.company.parking.model.dto.ParkingSessionDto;
import com.company.parking.model.dto.ParkingTicket;
import com.company.parking.service.parkingSession.ParkingSessionService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/parking")
public class ParkingSessionController {
    private final ParkingSessionService parkingSessionService;

    public ParkingSessionController(ParkingSessionService sessionService) {
        this.parkingSessionService = sessionService;
    }

    @GetMapping()
    public Iterable<ParkingSessionDto> getAllSessions() {
        return parkingSessionService.getAllAndMapItToDto();
    }

    @DeleteMapping()
    public void cancelUserSession() {
        parkingSessionService.deleteUserSession();
    }

    @GetMapping("/nonExpired")
    public Iterable<ParkingSessionDto> getAllNonExpiredSessions() {
        return parkingSessionService.getAllNonExpired();
    }

    @GetMapping("/active")
    public Iterable<ParkingSessionDto> getAllActiveSessions() {
        return parkingSessionService.getAllActive();
    }

    @GetMapping("/{id}")
    public ParkingSessionDto getSessionById(@PathVariable Long id) {
        return parkingSessionService.getById(id);
    }

    @PostMapping()
    public ParkingTicket createParkingSession(@RequestBody ParkingRequest request,
                                              @RequestParam(required = false) Boolean anySlot) {
        if (anySlot != null && anySlot) {
            LocalDateTime startAt = request.getStartAt();
            LocalDateTime endAt = request.getEndAt();
            return parkingSessionService.createNew(startAt, endAt);
        }
        return parkingSessionService.createNew(request);
    }
}
