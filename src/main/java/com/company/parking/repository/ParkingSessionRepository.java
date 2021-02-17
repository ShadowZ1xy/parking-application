package com.company.parking.repository;

import com.company.parking.model.ParkingSession;
import com.company.parking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ParkingSessionRepository extends JpaRepository<ParkingSession, Long> {
    List<ParkingSession> getAllByUser(User user);

    void deleteAllByUser(User user);
}
