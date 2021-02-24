package com.company.parking.util;

import com.company.parking.model.ParkingSession;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.TimeZone;

@Component
public class TimeTool {
    public final String applicationTimeZone = "Asia/Yerevan";

    public LocalDateTime getCurrentTime() {
        return LocalDateTime.now(
                TimeZone.getTimeZone(applicationTimeZone).toZoneId());
    }

    private boolean checkIfDateIsBetweenOtherTwoDate(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        return (date.isAfter(start) || date.isEqual(start)) &&
                (date.isBefore(end) || date.isEqual(end));
    }

    public boolean isSessionHaveConflictWithDateRange(LocalDateTime startDate, LocalDateTime endDate, ParkingSession session) {
        return checkIfDateIsBetweenOtherTwoDate(startDate, session.getStartAt(), session.getEndAt())
                || checkIfDateIsBetweenOtherTwoDate(endDate, session.getStartAt(), session.getEndAt())
                || checkIfDateIsBetweenOtherTwoDate(session.getStartAt(), startDate, endDate)
                || checkIfDateIsBetweenOtherTwoDate(session.getEndAt(), startDate, endDate);
    }
}
