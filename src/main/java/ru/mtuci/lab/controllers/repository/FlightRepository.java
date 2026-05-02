package ru.mtuci.lab.controllers.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.lab.controllers.entity.Flight;

public interface FlightRepository extends JpaRepository<Flight, Long> {

    boolean existsByFlightNumber(String flightNumber);

    List<Flight> findByDepartureCityIgnoreCaseAndArrivalCityIgnoreCaseAndDepartureTimeBetween(
            String departureCity,
            String arrivalCity,
            LocalDateTime start,
            LocalDateTime end
    );
}
