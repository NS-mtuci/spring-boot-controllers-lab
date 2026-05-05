package ru.mtuci.lab.controllers.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mtuci.lab.controllers.controller.BusinessRuleException;
import ru.mtuci.lab.controllers.controller.ResourceNotFoundException;
import ru.mtuci.lab.controllers.dto.airline.FlightRequest;
import ru.mtuci.lab.controllers.dto.airline.FlightResponse;
import ru.mtuci.lab.controllers.entity.Aircraft;
import ru.mtuci.lab.controllers.entity.Airport;
import ru.mtuci.lab.controllers.entity.BookingStatus;
import ru.mtuci.lab.controllers.entity.Flight;
import ru.mtuci.lab.controllers.entity.FlightStatus;
import ru.mtuci.lab.controllers.repository.BookingRepository;
import ru.mtuci.lab.controllers.repository.FlightRepository;

@Service
public class FlightService {

    private final FlightRepository flightRepository;
    private final AircraftService aircraftService;
    private final AirportService airportService;
    private final BookingRepository bookingRepository;
    private final AirlineMapper mapper;

    public FlightService(
            FlightRepository flightRepository,
            AircraftService aircraftService,
            AirportService airportService,
            BookingRepository bookingRepository,
            AirlineMapper mapper
    ) {
        this.flightRepository = flightRepository;
        this.aircraftService = aircraftService;
        this.airportService = airportService;
        this.bookingRepository = bookingRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<FlightResponse> findAll() {
        return flightRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public FlightResponse findById(long id) {
        return mapper.toResponse(getById(id));
    }

    @Transactional
    public FlightResponse create(FlightRequest request) {
        if (flightRepository.existsByFlightNumber(request.flightNumber())) {
            throw new BusinessRuleException("Flight number must be unique");
        }
        Aircraft aircraft = aircraftService.getById(request.aircraftId());
        Airport departureAirport = airportService.getById(request.departureAirportId());
        Airport arrivalAirport = airportService.getById(request.arrivalAirportId());
        validateRoute(departureAirport, arrivalAirport);
        Flight flight = new Flight(
                request.flightNumber(),
                departureAirport,
                arrivalAirport,
                request.departureTime(),
                aircraft,
                parseStatus(request.status())
        );
        return mapper.toResponse(flightRepository.save(flight));
    }

    @Transactional
    public FlightResponse update(long id, FlightRequest request) {
        Flight flight = getById(id);
        flightRepository.findByFlightNumber(request.flightNumber())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessRuleException("Flight number must be unique");
                });
        Aircraft aircraft = aircraftService.getById(request.aircraftId());
        Airport departureAirport = airportService.getById(request.departureAirportId());
        Airport arrivalAirport = airportService.getById(request.arrivalAirportId());
        validateRoute(departureAirport, arrivalAirport);
        validateAircraftCapacity(flight, aircraft);
        flight.setFlightNumber(request.flightNumber());
        flight.setDepartureAirport(departureAirport);
        flight.setArrivalAirport(arrivalAirport);
        flight.setDepartureTime(request.departureTime());
        flight.setAircraft(aircraft);
        flight.setStatus(parseStatus(request.status()));
        return mapper.toResponse(flight);
    }

    @Transactional
    public void delete(long id) {
        flightRepository.delete(getById(id));
    }

    @Transactional(readOnly = true)
    public List<FlightResponse> search(String departureAirportCode, String arrivalAirportCode, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        return flightRepository.findByDepartureAirportCodeIgnoreCaseAndArrivalAirportCodeIgnoreCaseAndDepartureTimeBetween(
                departureAirportCode,
                arrivalAirportCode,
                start,
                end
        ).stream().map(mapper::toResponse).toList();
    }

    @Transactional
    public FlightResponse changeStatus(long id, String status) {
        Flight flight = getById(id);
        flight.setStatus(parseStatus(status));
        return mapper.toResponse(flight);
    }

    @Transactional
    public FlightResponse assignAircraft(long flightId, long aircraftId) {
        Flight flight = getById(flightId);
        Aircraft aircraft = aircraftService.getById(aircraftId);
        validateAircraftCapacity(flight, aircraft);
        flight.setAircraft(aircraft);
        return mapper.toResponse(flight);
    }

    public Flight getById(long id) {
        return flightRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Flight", id));
    }

    private FlightStatus parseStatus(String status) {
        try {
            return FlightStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BusinessRuleException("Unknown flight status: " + status);
        }
    }

    private void validateRoute(Airport departureAirport, Airport arrivalAirport) {
        if (departureAirport.getId().equals(arrivalAirport.getId())) {
            throw new BusinessRuleException("Departure and arrival airports must be different");
        }
    }

    private void validateAircraftCapacity(Flight flight, Aircraft aircraft) {
        long activeBookings = bookingRepository.countByFlightAndStatusNot(flight, BookingStatus.CANCELLED);
        if (activeBookings > aircraft.getSeats()) {
            throw new BusinessRuleException("Aircraft capacity is lower than active booking count");
        }
    }
}
