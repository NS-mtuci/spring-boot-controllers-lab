package ru.mtuci.lab.controllers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.mtuci.lab.controllers.entity.Aircraft;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    boolean existsByTailNumber(String tailNumber);
}
