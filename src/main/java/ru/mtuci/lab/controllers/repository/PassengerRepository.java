package ru.mtuci.lab.controllers.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.lab.controllers.entity.Passenger;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {

    boolean existsByEmail(String email);

    boolean existsByDocumentNumber(String documentNumber);

    Optional<Passenger> findByEmail(String email);

    Optional<Passenger> findByDocumentNumber(String documentNumber);
}
