package ru.mtuci.lab.controllers.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mtuci.lab.controllers.controller.BusinessRuleException;
import ru.mtuci.lab.controllers.controller.ResourceNotFoundException;
import ru.mtuci.lab.controllers.dto.airline.PassengerRequest;
import ru.mtuci.lab.controllers.dto.airline.PassengerResponse;
import ru.mtuci.lab.controllers.entity.Passenger;
import ru.mtuci.lab.controllers.repository.PassengerRepository;

@Service
public class PassengerService {

    private final PassengerRepository passengerRepository;
    private final AirlineMapper mapper;

    public PassengerService(PassengerRepository passengerRepository, AirlineMapper mapper) {
        this.passengerRepository = passengerRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<PassengerResponse> findAll() {
        return passengerRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public PassengerResponse findById(long id) {
        return mapper.toResponse(getById(id));
    }

    @Transactional
    public PassengerResponse create(PassengerRequest request) {
        validateUnique(request.email(), request.documentNumber(), null);
        Passenger passenger = new Passenger(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.documentNumber()
        );
        return mapper.toResponse(passengerRepository.save(passenger));
    }

    @Transactional
    public PassengerResponse update(long id, PassengerRequest request) {
        Passenger passenger = getById(id);
        validateUnique(request.email(), request.documentNumber(), id);
        passenger.setFirstName(request.firstName());
        passenger.setLastName(request.lastName());
        passenger.setEmail(request.email());
        passenger.setDocumentNumber(request.documentNumber());
        return mapper.toResponse(passenger);
    }

    @Transactional
    public void delete(long id) {
        passengerRepository.delete(getById(id));
    }

    public Passenger getById(long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Passenger", id));
    }

    private void validateUnique(String email, String documentNumber, Long currentPassengerId) {
        passengerRepository.findByEmail(email)
                .filter(existing -> !existing.getId().equals(currentPassengerId))
                .ifPresent(existing -> {
                    throw new BusinessRuleException("Passenger email must be unique");
                });
        passengerRepository.findByDocumentNumber(documentNumber)
                .filter(existing -> !existing.getId().equals(currentPassengerId))
                .ifPresent(existing -> {
                    throw new BusinessRuleException("Passenger document number must be unique");
                });
    }
}
