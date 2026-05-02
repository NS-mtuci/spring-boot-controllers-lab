package ru.mtuci.lab.controllers.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mtuci.lab.controllers.controller.BusinessRuleException;
import ru.mtuci.lab.controllers.controller.ResourceNotFoundException;
import ru.mtuci.lab.controllers.dto.airline.AircraftRequest;
import ru.mtuci.lab.controllers.dto.airline.AircraftResponse;
import ru.mtuci.lab.controllers.entity.Aircraft;
import ru.mtuci.lab.controllers.repository.AircraftRepository;

@Service
public class AircraftService {

    private final AircraftRepository aircraftRepository;
    private final AirlineMapper mapper;

    public AircraftService(AircraftRepository aircraftRepository, AirlineMapper mapper) {
        this.aircraftRepository = aircraftRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<AircraftResponse> findAll() {
        return aircraftRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AircraftResponse findById(long id) {
        return mapper.toResponse(getById(id));
    }

    @Transactional
    public AircraftResponse create(AircraftRequest request) {
        if (aircraftRepository.existsByTailNumber(request.tailNumber())) {
            throw new BusinessRuleException("Aircraft tail number must be unique");
        }
        Aircraft aircraft = new Aircraft(request.model(), request.tailNumber(), request.seats());
        return mapper.toResponse(aircraftRepository.save(aircraft));
    }

    @Transactional
    public AircraftResponse update(long id, AircraftRequest request) {
        Aircraft aircraft = getById(id);
        aircraft.setModel(request.model());
        aircraft.setTailNumber(request.tailNumber());
        aircraft.setSeats(request.seats());
        return mapper.toResponse(aircraft);
    }

    @Transactional
    public void delete(long id) {
        aircraftRepository.delete(getById(id));
    }

    public Aircraft getById(long id) {
        return aircraftRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", id));
    }
}
