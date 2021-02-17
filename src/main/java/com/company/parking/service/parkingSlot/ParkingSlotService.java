package com.company.parking.service.parkingSlot;

import com.company.parking.model.ParkingSlot;
import com.company.parking.model.dto.ParkingRequest;
import com.company.parking.repository.ParkingSessionRepository;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;


public interface ParkingSlotService {
    void addNewParkingSlot(Iterable<ParkingSlot> slots);

    List<ParkingSlot> getAll();

    ResponseEntity<String> deleteById(Long id);

    ParkingSlot getById(Long id);

    boolean isSlotAlreadyTaken(ParkingRequest request, ParkingSessionRepository sessionRepository);

    List<ParkingSlot> getFreeSlotsInTimeRange(LocalDateTime start, LocalDateTime end,
                                              ParkingSlotService parkingSlotService,
                                              ParkingSessionRepository sessionRepository);
}
