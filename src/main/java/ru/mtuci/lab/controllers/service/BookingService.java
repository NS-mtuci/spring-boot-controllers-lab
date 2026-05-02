package ru.mtuci.lab.controllers.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mtuci.lab.controllers.controller.BusinessRuleException;
import ru.mtuci.lab.controllers.controller.ResourceNotFoundException;
import ru.mtuci.lab.controllers.dto.airline.BookSeatRequest;
import ru.mtuci.lab.controllers.dto.airline.BookingRequest;
import ru.mtuci.lab.controllers.dto.airline.BookingResponse;
import ru.mtuci.lab.controllers.entity.Booking;
import ru.mtuci.lab.controllers.entity.BookingStatus;
import ru.mtuci.lab.controllers.entity.Flight;
import ru.mtuci.lab.controllers.entity.FlightStatus;
import ru.mtuci.lab.controllers.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final AirlineMapper mapper;

    public BookingService(BookingRepository bookingRepository, FlightService flightService, AirlineMapper mapper) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> findAll() {
        return bookingRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public BookingResponse findById(long id) {
        return mapper.toResponse(getById(id));
    }

    @Transactional
    public BookingResponse create(BookingRequest request) {
        Booking booking = new Booking(
                request.passengerName(),
                flightService.getById(request.flightId()),
                request.seatNumber(),
                parseStatus(request.status())
        );
        validateSeatAvailability(booking.getFlight(), booking.getSeatNumber(), booking.getId());
        return mapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponse update(long id, BookingRequest request) {
        Booking booking = getById(id);
        booking.setPassengerName(request.passengerName());
        booking.setFlight(flightService.getById(request.flightId()));
        booking.setSeatNumber(request.seatNumber());
        booking.setStatus(parseStatus(request.status()));
        validateSeatAvailability(booking.getFlight(), booking.getSeatNumber(), id);
        return mapper.toResponse(booking);
    }

    @Transactional
    public void delete(long id) {
        bookingRepository.delete(getById(id));
    }

    @Transactional
    public BookingResponse bookSeat(BookSeatRequest request) {
        Flight flight = flightService.getById(request.flightId());
        if (flight.getStatus() == FlightStatus.CANCELLED || flight.getStatus() == FlightStatus.DEPARTED) {
            throw new BusinessRuleException("Cannot book a seat for this flight status");
        }
        validateSeatAvailability(flight, request.seatNumber(), null);
        long activeBookings = bookingRepository.countByFlightAndStatusNot(flight, BookingStatus.CANCELLED);
        if (activeBookings >= flight.getAircraft().getSeats()) {
            throw new BusinessRuleException("No available seats for this flight");
        }
        Booking booking = new Booking(request.passengerName(), flight, request.seatNumber(), BookingStatus.CONFIRMED);
        return mapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponse cancel(long id) {
        Booking booking = getById(id);
        booking.setStatus(BookingStatus.CANCELLED);
        return mapper.toResponse(booking);
    }

    @Transactional
    public BookingResponse checkIn(long id) {
        Booking booking = getById(id);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new BusinessRuleException("Only confirmed bookings can be checked in");
        }
        booking.setStatus(BookingStatus.CHECKED_IN);
        return mapper.toResponse(booking);
    }

    @Transactional(readOnly = true)
    public List<BookingResponse> findByFlight(long flightId) {
        flightService.getById(flightId);
        return bookingRepository.findByFlightId(flightId).stream().map(mapper::toResponse).toList();
    }

    public Booking getById(long id) {
        return bookingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
    }

    private void validateSeatAvailability(Flight flight, String seatNumber, Long currentBookingId) {
        Booking occupied = bookingRepository.findByFlightAndSeatNumber(flight, seatNumber).orElse(null);
        if (occupied != null && (currentBookingId == null || !occupied.getId().equals(currentBookingId))) {
            throw new BusinessRuleException("Seat is already booked");
        }
    }

    private BookingStatus parseStatus(String status) {
        try {
            return BookingStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new BusinessRuleException("Unknown booking status: " + status);
        }
    }
}
