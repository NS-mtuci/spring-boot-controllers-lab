package ru.mtuci.lab.controllers.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.mtuci.lab.controllers.entity.Aircraft;
import ru.mtuci.lab.controllers.entity.Airport;
import ru.mtuci.lab.controllers.entity.Booking;
import ru.mtuci.lab.controllers.entity.BookingStatus;
import ru.mtuci.lab.controllers.entity.Flight;
import ru.mtuci.lab.controllers.entity.FlightStatus;
import ru.mtuci.lab.controllers.entity.Passenger;
import ru.mtuci.lab.controllers.repository.AircraftRepository;
import ru.mtuci.lab.controllers.repository.AirportRepository;
import ru.mtuci.lab.controllers.repository.BookingRepository;
import ru.mtuci.lab.controllers.repository.FlightRepository;
import ru.mtuci.lab.controllers.repository.PassengerRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAirlineData(
            AircraftRepository aircraftRepository,
            AirportRepository airportRepository,
            PassengerRepository passengerRepository,
            FlightRepository flightRepository,
            BookingRepository bookingRepository
    ) {
        return args -> {
            if (aircraftRepository.count() > 0) {
                return;
            }

            Aircraft airbus = aircraftRepository.save(new Aircraft("Airbus A320", "RA-73001", 180));
            Aircraft boeing = aircraftRepository.save(new Aircraft("Boeing 737-800", "RA-73102", 189));
            Aircraft sukhoi = aircraftRepository.save(new Aircraft("Sukhoi Superjet 100", "RA-89001", 98));

            Airport svo = airportRepository.save(new Airport("SVO", "Sheremetyevo International Airport", "Moscow", "Russia"));
            Airport led = airportRepository.save(new Airport("LED", "Pulkovo Airport", "Saint Petersburg", "Russia"));
            Airport kzn = airportRepository.save(new Airport("KZN", "Kazan International Airport", "Kazan", "Russia"));
            Airport aer = airportRepository.save(new Airport("AER", "Sochi International Airport", "Sochi", "Russia"));

            Passenger ivan = passengerRepository.save(new Passenger(
                    "Ivan",
                    "Petrov",
                    "ivan.petrov@example.com",
                    "4510-123456"
            ));
            Passenger anna = passengerRepository.save(new Passenger(
                    "Anna",
                    "Sidorova",
                    "anna.sidorova@example.com",
                    "4511-654321"
            ));

            Flight firstFlight = flightRepository.save(new Flight(
                    "SU100",
                    svo,
                    led,
                    LocalDateTime.now().plusDays(3).withSecond(0).withNano(0),
                    airbus,
                    FlightStatus.SCHEDULED
            ));
            Flight secondFlight = flightRepository.save(new Flight(
                    "SU220",
                    svo,
                    kzn,
                    LocalDateTime.now().plusDays(5).withSecond(0).withNano(0),
                    boeing,
                    FlightStatus.BOARDING
            ));
            flightRepository.save(new Flight(
                    "SU310",
                    svo,
                    aer,
                    LocalDateTime.now().plusDays(7).withSecond(0).withNano(0),
                    sukhoi,
                    FlightStatus.DELAYED
            ));

            bookingRepository.save(new Booking(ivan, firstFlight, "12A", BookingStatus.CONFIRMED));
            bookingRepository.save(new Booking(anna, secondFlight, "8C", BookingStatus.CHECKED_IN));
        };
    }
}
