package ru.mtuci.lab.controllers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.lab.controllers.entity.Airport;

public interface AirportRepository extends JpaRepository<Airport, Long> {

    boolean existsByCode(String code);

    Optional<Airport> findByCode(String code);
}
