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
import ru.mtuci.lab.controllers.entity.Passenger;
import ru.mtuci.lab.controllers.repository.BookingRepository;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final FlightService flightService;
    private final PassengerService passengerService;
    private final AirlineMapper mapper;

    public BookingService(
            BookingRepository bookingRepository,
            FlightService flightService,
            PassengerService passengerService,
            AirlineMapper mapper
    ) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.passengerService = passengerService;
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
        Flight flight = flightService.getById(request.flightId());
        Passenger passenger = passengerService.getById(request.passengerId());
        BookingStatus status = parseStatus(request.status());
        validateBookingState(flight, request.seatNumber(), status, null);
        Booking booking = new Booking(passenger, flight, request.seatNumber(), status);
        return mapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponse update(long id, BookingRequest request) {
        Booking booking = getById(id);
        Flight flight = flightService.getById(request.flightId());
        Passenger passenger = passengerService.getById(request.passengerId());
        BookingStatus status = parseStatus(request.status());
        validateBookingState(flight, request.seatNumber(), status, id);
        booking.setPassenger(passenger);
        booking.setFlight(flight);
        booking.setSeatNumber(request.seatNumber());
        booking.setStatus(status);
        return mapper.toResponse(booking);
    }

    @Transactional
    public void delete(long id) {
        bookingRepository.delete(getById(id));
    }

    @Transactional
    public BookingResponse bookSeat(BookSeatRequest request) {
        Flight flight = flightService.getById(request.flightId());
        Passenger passenger = passengerService.getById(request.passengerId());
        validateBookingState(flight, request.seatNumber(), BookingStatus.CONFIRMED, null);
        Booking booking = new Booking(passenger, flight, request.seatNumber(), BookingStatus.CONFIRMED);
        return mapper.toResponse(bookingRepository.save(booking));
    }

    @Transactional
    public BookingResponse cancel(long id) {
        Booking booking = getById(id);
        validateRefundAllowed(booking.getFlight());
        booking.setStatus(BookingStatus.CANCELLED);
        return mapper.toResponse(booking);
    }

    @Transactional
    public BookingResponse checkIn(long id) {
        Booking booking = getById(id);
        if (booking.getStatus() != BookingStatus.CONFIRMED) {
            throw new BusinessRuleException("Only confirmed bookings can be checked in");
        }
        if (booking.getFlight().getStatus() != FlightStatus.BOARDING) {
            throw new BusinessRuleException("Check-in is available only while flight is boarding");
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
        Booking occupied = bookingRepository.findByFlightAndSeatNumberAndStatusNot(
                flight,
                seatNumber,
                BookingStatus.CANCELLED
        ).orElse(null);
        if (occupied != null && (currentBookingId == null || !occupied.getId().equals(currentBookingId))) {
            throw new BusinessRuleException("Seat is already booked");
        }
    }

    private void validateBookingState(Flight flight, String seatNumber, BookingStatus status, Long currentBookingId) {
        if (status == BookingStatus.CANCELLED) {
            return;
        }
        validateSeatAvailability(flight, seatNumber, currentBookingId);
        validateCapacity(flight, currentBookingId);
        if (status == BookingStatus.CHECKED_IN) {
            if (flight.getStatus() != FlightStatus.BOARDING) {
                throw new BusinessRuleException("Checked-in booking requires a boarding flight");
            }
            return;
        }
        validateBookingAllowed(flight);
    }

    private void validateBookingAllowed(Flight flight) {
        if (flight.getStatus() != FlightStatus.SCHEDULED && flight.getStatus() != FlightStatus.DELAYED) {
            throw new BusinessRuleException("Cannot book a seat for this flight status");
        }
    }

    private void validateRefundAllowed(Flight flight) {
        if (flight.getStatus() == FlightStatus.BOARDING || flight.getStatus() == FlightStatus.DEPARTED) {
            throw new BusinessRuleException("Cannot cancel booking for this flight status");
        }
    }

    private void validateCapacity(Flight flight, Long currentBookingId) {
        long activeBookings = bookingRepository.countByFlightAndStatusNot(flight, BookingStatus.CANCELLED);
        if (currentBookingId != null) {
            Booking currentBooking = bookingRepository.findById(currentBookingId).orElse(null);
            if (currentBooking != null && currentBooking.getFlight().getId().equals(flight.getId())
                    && currentBooking.getStatus() != BookingStatus.CANCELLED) {
                activeBookings--;
            }
        }
        if (activeBookings >= flight.getAircraft().getSeats()) {
            throw new BusinessRuleException("No available seats for this flight");
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
