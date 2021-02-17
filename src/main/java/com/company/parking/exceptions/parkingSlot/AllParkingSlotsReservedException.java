package com.company.parking.exceptions.parkingSlot;

import com.company.parking.exceptions.ParkingAppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "All parking slots are reserved")
public class AllParkingSlotsReservedException extends ParkingAppException {
}
