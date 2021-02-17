package com.company.parking.model.dto;

import com.company.parking.exceptions.NotValidFormException;
import com.company.parking.model.ParkingSlot;
import com.company.parking.service.parkingSlot.ParkingSlotService;
import com.company.parking.util.TimeTool;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class ParkingRequest {

    private Long slotId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime endAt;
    private Integer durationHours;
    private Integer durationMinutes;

    public ParkingRequest() {
    }

    public ParkingRequest(Long slotId, LocalDateTime startAt, LocalDateTime endAt) {
        this.slotId = slotId;
        this.startAt = startAt;
        this.endAt = endAt;
    }

    public ParkingRequest(Long slotId, LocalDateTime startAt, Integer durationHours, Integer durationMinutes) {
        this.slotId = slotId;
        this.startAt = startAt;
        this.durationHours = durationHours;
        this.durationMinutes = durationMinutes;
    }

    public LocalDateTime getEndAt() {
        return endAt;
    }

    public void setEndAt(LocalDateTime endAt) {
        this.endAt = endAt;
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

    public Integer getDurationHours() {
        return durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * @param timeTool for current time info
     * @return true if or validation is passed
     * return false if one/many required fields is empty/null
     */
    private boolean isValidFields(TimeTool timeTool) {
        boolean isValid;
        LocalDateTime currentTime = timeTool.getCurrentTime();
        isValid = currentTime.isBefore(this.startAt);
        if (this.getDurationHours() == null
                && this.getDurationMinutes() == null
                && this.getEndAt() == null) {
            isValid = false;
        } else if (this.getEndAt() != null) {
            if (getEndAt().isBefore(this.startAt)
                    || getEndAt().isEqual(this.startAt)) {
                isValid = false;
            }
        }
        return isValid;
    }

    /**
     * Calculate LocalDateTime endAt based in this.DurationHours and this.DurationMinutes
     *
     * @return endDate
     */
    public LocalDateTime calculateEndDate() {
        LocalDateTime endDate = this.getStartAt();
        if (this.getEndAt() != null) {
            return this.getEndAt();
        }
        if (this.getDurationHours() != null) {
            endDate = endDate.plus(this.getDurationHours(), ChronoUnit.HOURS);
        }
        if (this.getDurationMinutes() != null) {
            endDate = endDate.plus(this.getDurationMinutes(), ChronoUnit.MINUTES);
        }
        return endDate;
    }

    /**
     * @param parkingSlotService for slot info
     * @param timeTool           for current time
     * @return ParkingSlot if all validations is passed
     */
    public ParkingSlot getSlotIfValidFields(ParkingSlotService parkingSlotService, TimeTool timeTool) {
        if (this.getSlotId() == null) {
            throw new NotValidFormException();
        }
        ParkingSlot parkingSlot = parkingSlotService.getById(this.getSlotId());
        if (!this.isValidFields(timeTool) || parkingSlot == null) {
            throw new NotValidFormException();
        }
        return parkingSlot;
    }
}
