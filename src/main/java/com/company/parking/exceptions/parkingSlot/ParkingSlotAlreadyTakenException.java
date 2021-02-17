package com.company.parking.exceptions.parkingSlot;

import com.company.parking.exceptions.ParkingAppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Parking slot already taken")
public class ParkingSlotAlreadyTakenException extends ParkingAppException {
}
