package ru.mtuci.lab.controllers.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.mtuci.lab.controllers.controller.BusinessRuleException;
import ru.mtuci.lab.controllers.controller.ResourceNotFoundException;
import ru.mtuci.lab.controllers.dto.airline.AirportRequest;
import ru.mtuci.lab.controllers.dto.airline.AirportResponse;
import ru.mtuci.lab.controllers.entity.Airport;
import ru.mtuci.lab.controllers.repository.AirportRepository;

@Service
public class AirportService {

    private final AirportRepository airportRepository;
    private final AirlineMapper mapper;

    public AirportService(AirportRepository airportRepository, AirlineMapper mapper) {
        this.airportRepository = airportRepository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<AirportResponse> findAll() {
        return airportRepository.findAll().stream().map(mapper::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public AirportResponse findById(long id) {
        return mapper.toResponse(getById(id));
    }

    @Transactional
    public AirportResponse create(AirportRequest request) {
        if (airportRepository.existsByCode(request.code())) {
            throw new BusinessRuleException("Airport code must be unique");
        }
        Airport airport = new Airport(request.code(), request.name(), request.city(), request.country());
        return mapper.toResponse(airportRepository.save(airport));
    }

    @Transactional
    public AirportResponse update(long id, AirportRequest request) {
        Airport airport = getById(id);
        airportRepository.findByCode(request.code())
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> {
                    throw new BusinessRuleException("Airport code must be unique");
                });
        airport.setCode(request.code());
        airport.setName(request.name());
        airport.setCity(request.city());
        airport.setCountry(request.country());
        return mapper.toResponse(airport);
    }

    @Transactional
    public void delete(long id) {
        airportRepository.delete(getById(id));
    }

    public Airport getById(long id) {
        return airportRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Airport", id));
    }
}
