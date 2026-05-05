package ru.mtuci.lab.controllers.controller.airline;

import java.time.LocalDate;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import ru.mtuci.lab.controllers.dto.airline.AssignAircraftRequest;
import ru.mtuci.lab.controllers.dto.airline.BookSeatRequest;
import ru.mtuci.lab.controllers.dto.airline.BookingResponse;
import ru.mtuci.lab.controllers.dto.airline.FlightResponse;
import ru.mtuci.lab.controllers.dto.airline.FlightStatusRequest;
import ru.mtuci.lab.controllers.service.BookingService;
import ru.mtuci.lab.controllers.service.FlightService;

@RestController
@RequestMapping("/api/airline/operations")
public class AirlineOperationController {

    private final FlightService flightService;
    private final BookingService bookingService;

    public AirlineOperationController(FlightService flightService, BookingService bookingService) {
        this.flightService = flightService;
        this.bookingService = bookingService;
    }

    @GetMapping("/flights/search")
    public List<FlightResponse> searchFlights(
            @RequestParam String departureAirportCode,
            @RequestParam String arrivalAirportCode,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        return flightService.search(departureAirportCode, arrivalAirportCode, date);
    }

    @PostMapping("/bookings")
    public BookingResponse bookSeat(@Valid @RequestBody BookSeatRequest request) {
        return bookingService.bookSeat(request);
    }

    @PostMapping("/bookings/{id}/cancel")
    public BookingResponse cancelBooking(@PathVariable long id) {
        return bookingService.cancel(id);
    }

    @PostMapping("/bookings/{id}/check-in")
    public BookingResponse checkIn(@PathVariable long id) {
        return bookingService.checkIn(id);
    }

    @GetMapping("/flights/{id}/bookings")
    public List<BookingResponse> getFlightBookings(@PathVariable long id) {
        return bookingService.findByFlight(id);
    }

    @PostMapping("/flights/{id}/status")
    public FlightResponse changeFlightStatus(@PathVariable long id, @Valid @RequestBody FlightStatusRequest request) {
        return flightService.changeStatus(id, request.status());
    }

    @PostMapping("/flights/{id}/aircraft")
    public FlightResponse assignAircraft(@PathVariable long id, @Valid @RequestBody AssignAircraftRequest request) {
        return flightService.assignAircraft(id, request.aircraftId());
    }
}
