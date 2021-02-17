package com.company.parking.controller;

import com.company.parking.model.ParkingSlot;
import com.company.parking.service.parkingSlot.ParkingSlotService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parkingSlot")
public class ParkingSlotController {
    private final ParkingSlotService parkingSlotService;

    public ParkingSlotController(ParkingSlotService parkingSlotService) {
        this.parkingSlotService = parkingSlotService;
    }

    @GetMapping()
    public List<ParkingSlot> getAllParkingSlots() {
        return parkingSlotService.getAll();
    }

    @GetMapping("/{id}")
    public ParkingSlot getParkingSlot(@PathVariable Long id) {
        return parkingSlotService.getById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> deleteParkingSlot(@PathVariable Long id) {
        return parkingSlotService.deleteById(id);
    }

    @PostMapping()
    @PreAuthorize("hasAuthority('admin')")
    public ResponseEntity<String> addNewParkingSlot(
            @RequestBody Iterable<ParkingSlot> slots) {
        parkingSlotService.addNewParkingSlot(slots);
        return new ResponseEntity<>("New parking slots successfully added", HttpStatus.OK);
    }
}
