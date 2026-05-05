package ru.mtuci.lab.controllers.service;

import org.springframework.stereotype.Component;

import ru.mtuci.lab.controllers.dto.airline.AircraftResponse;
import ru.mtuci.lab.controllers.dto.airline.AirportResponse;
import ru.mtuci.lab.controllers.dto.airline.BookingResponse;
import ru.mtuci.lab.controllers.dto.airline.FlightResponse;
import ru.mtuci.lab.controllers.dto.airline.PassengerResponse;
import ru.mtuci.lab.controllers.entity.Aircraft;
import ru.mtuci.lab.controllers.entity.Airport;
import ru.mtuci.lab.controllers.entity.Booking;
import ru.mtuci.lab.controllers.entity.Flight;
import ru.mtuci.lab.controllers.entity.Passenger;

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

    public AirportResponse toResponse(Airport airport) {
        return new AirportResponse(
                airport.getId(),
                airport.getCode(),
                airport.getName(),
                airport.getCity(),
                airport.getCountry()
        );
    }

    public PassengerResponse toResponse(Passenger passenger) {
        return new PassengerResponse(
                passenger.getId(),
                passenger.getFirstName(),
                passenger.getLastName(),
                passenger.getEmail(),
                passenger.getDocumentNumber()
        );
    }

    public FlightResponse toResponse(Flight flight) {
        return new FlightResponse(
                flight.getId(),
                flight.getFlightNumber(),
                flight.getDepartureAirport().getId(),
                flight.getDepartureAirport().getCode(),
                flight.getArrivalAirport().getId(),
                flight.getArrivalAirport().getCode(),
                flight.getDepartureTime(),
                flight.getAircraft().getId(),
                flight.getStatus().name()
        );
    }

    public BookingResponse toResponse(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getPassenger().getId(),
                booking.getPassenger().getFirstName(),
                booking.getPassenger().getLastName(),
                booking.getFlight().getId(),
                booking.getSeatNumber(),
                booking.getStatus().name()
        );
    }
}
