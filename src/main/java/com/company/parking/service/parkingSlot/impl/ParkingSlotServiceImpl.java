package com.company.parking.service.parkingSlot.impl;

import com.company.parking.exceptions.EntityNotFoundException;
import com.company.parking.model.ParkingSession;
import com.company.parking.model.ParkingSlot;
import com.company.parking.model.dto.ParkingRequest;
import com.company.parking.repository.ParkingSessionRepository;
import com.company.parking.repository.ParkingSlotRepository;
import com.company.parking.service.parkingSlot.ParkingSlotService;
import com.company.parking.util.TimeTool;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {
    private final ParkingSlotRepository parkingSlotRepository;
    private final TimeTool timeTool;

    public ParkingSlotServiceImpl(ParkingSlotRepository parkingSlotRepository, TimeTool timeTool) {
        this.parkingSlotRepository = parkingSlotRepository;
        this.timeTool = timeTool;
    }

    @Override
    public void addNewParkingSlot(Iterable<ParkingSlot> slots) {
        parkingSlotRepository.saveAll(slots);
    }

    @Override
    public List<ParkingSlot> getAll() {
        return parkingSlotRepository.findAll();
    }

    @Override
    public ResponseEntity<String> deleteById(Long id) {
        Optional<ParkingSlot> optionalParkingSlot = parkingSlotRepository.findById(id);
        if (optionalParkingSlot.isPresent()) {
            parkingSlotRepository.deleteById(id);
            return new ResponseEntity<>("Slot successfully deleted.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Slot with id: " + id + " doesn't exists.", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ParkingSlot getById(Long id) {
        Optional<ParkingSlot> parkingSlotOptional = parkingSlotRepository
                .findById(id);
        return parkingSlotOptional.orElseThrow(EntityNotFoundException::new);
    }


    /**
     * Check is slot from user request already taken
     *
     * @param request from user
     * @return true if slot reserved, false if not
     */
    @Override
    public boolean isSlotAlreadyTaken(ParkingRequest request,
                                      ParkingSessionRepository sessionRepository) {
        List<ParkingSession> allSessions = sessionRepository.findAll();
        LocalDateTime startDate = request.getStartAt();
        LocalDateTime endDate = request.calculateEndDate();
        return allSessions.stream()
                .filter((session) -> session.getParkingSlot().getId().equals(request.getSlotId()))
                .anyMatch((session) -> timeTool.isSessionHaveConflictWithDateRange(startDate, endDate, session));
    }

    @Override
    public List<ParkingSlot> getFreeSlotsInTimeRange(LocalDateTime start,
                                                     LocalDateTime end,
                                                     ParkingSlotService parkingSlotService,
                                                     ParkingSessionRepository sessionRepository) {
        List<ParkingSlot> slots = parkingSlotService.getAll();
        List<ParkingSession> sessions = sessionRepository.findAll();

        List<ParkingSlot> reservedSlots = sessions.stream()
                .filter((session) -> timeTool.isSessionHaveConflictWithDateRange(start, end, session))
                .map(ParkingSession::getParkingSlot)
                .collect(Collectors.toList());
        slots.removeAll(reservedSlots);
        return slots;
    }
}
