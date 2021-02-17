package com.company.parking.exceptions.user;

import com.company.parking.exceptions.ParkingAppException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "Bad credentials")
public class UserRegistrationException extends ParkingAppException {
}
