package ru.mtuci.lab.controllers.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.lab.controllers.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    Optional<Flight> findByFlightNumber(String flightNumber);

    List<Flight> findByDepartureAirportCodeIgnoreCaseAndArrivalAirportCodeIgnoreCaseAndDepartureTimeBetween(
            String departureAirportCode,
            String arrivalAirportCode,
            LocalDateTime start,
            LocalDateTime end
    );
}
