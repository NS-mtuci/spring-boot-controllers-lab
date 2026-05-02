package ru.mtuci.lab.controllers.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.mtuci.lab.controllers.entity.Aircraft;
import ru.mtuci.lab.controllers.entity.Booking;
import ru.mtuci.lab.controllers.entity.BookingStatus;
import ru.mtuci.lab.controllers.entity.Flight;
import ru.mtuci.lab.controllers.entity.FlightStatus;
import ru.mtuci.lab.controllers.repository.AircraftRepository;
import ru.mtuci.lab.controllers.repository.BookingRepository;
import ru.mtuci.lab.controllers.repository.FlightRepository;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner seedAirlineData(
            AircraftRepository aircraftRepository,
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

            Flight firstFlight = flightRepository.save(new Flight(
                    "SU100",
                    "Moscow",
                    "Saint Petersburg",
                    LocalDateTime.now().plusDays(3).withSecond(0).withNano(0),
                    airbus,
                    FlightStatus.SCHEDULED
            ));
            Flight secondFlight = flightRepository.save(new Flight(
                    "SU220",
                    "Moscow",
                    "Kazan",
                    LocalDateTime.now().plusDays(5).withSecond(0).withNano(0),
                    boeing,
                    FlightStatus.SCHEDULED
            ));
            flightRepository.save(new Flight(
                    "SU310",
                    "Moscow",
                    "Sochi",
                    LocalDateTime.now().plusDays(7).withSecond(0).withNano(0),
                    sukhoi,
                    FlightStatus.DELAYED
            ));

            bookingRepository.save(new Booking("Ivan Petrov", firstFlight, "12A", BookingStatus.CONFIRMED));
            bookingRepository.save(new Booking("Anna Sidorova", secondFlight, "8C", BookingStatus.CHECKED_IN));
        };
    }
}
