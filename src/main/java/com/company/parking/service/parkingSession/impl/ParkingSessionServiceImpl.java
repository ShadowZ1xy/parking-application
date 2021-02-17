package com.company.parking.service.parkingSession.impl;

import com.company.parking.exceptions.EntityNotFoundException;
import com.company.parking.exceptions.parkingSlot.AllParkingSlotsReservedException;
import com.company.parking.exceptions.parkingSlot.ParkingSlotAlreadyTakenException;
import com.company.parking.exceptions.user.UserAlreadyHaveOpenSessionException;
import com.company.parking.model.ParkingSession;
import com.company.parking.model.ParkingSlot;
import com.company.parking.model.User;
import com.company.parking.model.dto.ParkingRequest;
import com.company.parking.model.dto.ParkingSessionDto;
import com.company.parking.model.dto.ParkingTicket;
import com.company.parking.repository.ParkingSessionRepository;
import com.company.parking.service.parkingSession.ParkingSessionService;
import com.company.parking.service.parkingSlot.ParkingSlotService;
import com.company.parking.util.TimeTool;
import com.company.parking.util.UserUtil;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParkingSessionServiceImpl implements ParkingSessionService {
    private final UserUtil userUtil;
    private final ParkingSlotService parkingSlotService;
    private final ParkingSessionRepository sessionRepository;
    private final TimeTool timeTool;

    public ParkingSessionServiceImpl(UserUtil userUtil, ParkingSlotService parkingSlotService, ParkingSessionRepository sessionRepository, TimeTool timeTool) {
        this.userUtil = userUtil;
        this.parkingSlotService = parkingSlotService;
        this.sessionRepository = sessionRepository;
        this.timeTool = timeTool;
    }

    @Override
    public Iterable<ParkingSessionDto> getAllAndMapItToDto() {
        return sessionRepository
                .findAll().stream()
                .map((el) -> new ParkingSessionDto(el, this))
                .collect(Collectors.toList());
    }

    /**
     * Create new parking session if all validations is passed
     *
     * @param request from user
     * @return parking ticket based in new ParkingSession
     */
    @Override
    public ParkingTicket createNew(ParkingRequest request) {
        if (parkingSlotService.isSlotAlreadyTaken(request, sessionRepository)) {
            throw new ParkingSlotAlreadyTakenException();
        }
        if (isCurrentUserAlreadyHaveOpenSession()) {
            throw new UserAlreadyHaveOpenSessionException();
        }
        ParkingSlot parkingSlot = request.getSlotIfValidFields(parkingSlotService, timeTool);
        ParkingSession parkingSession = new ParkingSession();
        User user = userUtil.currentUser();
        setupParkingSessionFromRequest(request, parkingSlot, parkingSession, user);
        ParkingSession sessionFromDb = sessionRepository.save(parkingSession);
        return ParkingTicket.createTicketBasedOnSession(
                sessionFromDb,
                parkingSlot.getId(),
                user);
    }

    @Override
    public ParkingTicket createNew(LocalDateTime start, LocalDateTime end) {
        List<ParkingSlot> slots = parkingSlotService
                .getFreeSlotsInTimeRange(start, end,
                        parkingSlotService, sessionRepository);
        if (slots.isEmpty()) {
            throw new AllParkingSlotsReservedException();
        }
        ParkingRequest request = new ParkingRequest(slots.get(0).getId(), start, end);
        return createNew(request);
    }


    /**
     * @return true if user already have session where expired is false
     * else - false
     */
    private boolean isCurrentUserAlreadyHaveOpenSession() {
        User currentUser = userUtil.currentUser();
        List<ParkingSession> sessions = sessionRepository.getAllByUser(currentUser);
        return sessions.stream().anyMatch((el) -> !this.isExpired(el));
    }


    /**
     * @param id is session.id
     * @return Mapped dto from ParkingSession object
     */
    @Override
    public ParkingSessionDto getById(Long id) {
        ParkingSession parkingSession = sessionRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        return new ParkingSessionDto(parkingSession, this);
    }

    /**
     * @return mapped list of dto where isExpired is false
     */
    @Override
    public Iterable<ParkingSessionDto> getAllNonExpired() {
        return sessionRepository.findAll()
                .stream()
                .filter((el) -> !isExpired(el))
                .map((el) -> new ParkingSessionDto(el, this))
                .collect(Collectors.toList());
    }

    /**
     * @param request        from user
     * @param parkingSlot    user want to reserve
     * @param parkingSession empty session what need to be setup
     * @param user           who send request
     */
    private void setupParkingSessionFromRequest(ParkingRequest request,
                                                ParkingSlot parkingSlot,
                                                ParkingSession parkingSession,
                                                User user) {
        parkingSession.setUser(user);
        parkingSession.setStartAt(request.getStartAt());
        parkingSession.setEndAt(request.calculateEndDate());
        parkingSession.setParkingSlot(parkingSlot);
    }


    /**
     * @param session get ParkingSession
     * @return true if current time is after session.startAt and before session.endAt
     */
    @Override
    public boolean isActive(ParkingSession session) {
        LocalDateTime startAt = session.getStartAt();
        LocalDateTime endAt = session.getEndAt();
        LocalDateTime currentTime = timeTool.getCurrentTime();
        return currentTime.isBefore(endAt) && currentTime.isAfter(startAt);
    }

    /**
     * @return all active ParkingSession-s from repo
     */
    @Override
    public Iterable<ParkingSessionDto> getAllActive() {
        return sessionRepository.findAll()
                .stream()
                .filter(this::isActive)
                .map((el) -> new ParkingSessionDto(el, this))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteUserSession() {
        User currentUser = userUtil.currentUser();
        sessionRepository.deleteAllByUser(currentUser);
    }

    /**
     * @param session for check
     * @return true if current date is after session.endAt
     * else false if before
     */
    @Override
    public boolean isExpired(ParkingSession session) {
        LocalDateTime endAt = session.getEndAt();
        LocalDateTime currentTime = timeTool.getCurrentTime();
        return currentTime.isAfter(endAt);
    }
}
