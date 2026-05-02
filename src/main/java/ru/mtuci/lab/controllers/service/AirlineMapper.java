package ru.mtuci.lab.controllers.service;

import org.springframework.stereotype.Component;

import ru.mtuci.lab.controllers.dto.airline.AircraftResponse;
import ru.mtuci.lab.controllers.dto.airline.BookingResponse;
import ru.mtuci.lab.controllers.dto.airline.FlightResponse;
import ru.mtuci.lab.controllers.entity.Aircraft;
import ru.mtuci.lab.controllers.entity.Booking;
import ru.mtuci.lab.controllers.entity.Flight;

@Component
public class AirlineMapper {

    public AircraftResponse toResponse(Aircraft aircraft) {
        return new AircraftResponse(
                aircraft.getId(),
                aircraft.getModel(),
                aircraft.getTailNumber(),
                aircraft.getSeats()
        );
    }

    public FlightResponse toResponse(Flight flight) {
        return new FlightResponse(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureCity(),
                flight.getArrivalCity(),
                flight.getDepartureTime(),
                flight.getAircraft().getId(),
                flight.getStatus().name()
        );
    }

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getPassengerName(),
                booking.getFlight().getId(),
                booking.getSeatNumber(),
                booking.getStatus().name()
        );
    }
}
