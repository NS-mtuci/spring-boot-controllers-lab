package ru.mtuci.lab.controllers.controller.airline;

import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.mtuci.lab.controllers.dto.airline.AirportRequest;
import ru.mtuci.lab.controllers.dto.airline.AirportResponse;
import ru.mtuci.lab.controllers.service.AirportService;

@RestController
@RequestMapping("/api/airports")
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping
    public List<AirportResponse> findAll() {
        return airportService.findAll();
    }

    @GetMapping("/{id}")
    public AirportResponse findById(@PathVariable long id) {
        return airportService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AirportResponse create(@Valid @RequestBody AirportRequest request) {
        return airportService.create(request);
    }

    @PutMapping("/{id}")
    public AirportResponse update(@PathVariable long id, @Valid @RequestBody AirportRequest request) {
        return airportService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        airportService.delete(id);
    }
}
