package ru.mtuci.lab.controllers.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.lab.controllers.entity.Booking;
import ru.mtuci.lab.controllers.entity.BookingStatus;
import ru.mtuci.lab.controllers.entity.Flight;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    Optional<Booking> findByFlightAndSeatNumberAndStatusNot(
            Flight flight,
            String seatNumber,
            BookingStatus status
    );

    long countByFlightAndStatusNot(Flight flight, BookingStatus status);

    List<Booking> findByFlightId(long flightId);
}
