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

import ru.mtuci.lab.controllers.dto.airline.AircraftRequest;
import ru.mtuci.lab.controllers.dto.airline.AircraftResponse;
import ru.mtuci.lab.controllers.service.AircraftService;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final AircraftService aircraftService;

    public AircraftController(AircraftService aircraftService) {
        this.aircraftService = aircraftService;
    }

    @GetMapping
    public List<AircraftResponse> findAll() {
        return aircraftService.findAll();
    }

    @GetMapping("/{id}")
    public AircraftResponse findById(@PathVariable long id) {
        return aircraftService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AircraftResponse create(@Valid @RequestBody AircraftRequest request) {
        return aircraftService.create(request);
    }

    @PutMapping("/{id}")
    public AircraftResponse update(@PathVariable long id, @Valid @RequestBody AircraftRequest request) {
        return aircraftService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        aircraftService.delete(id);
    }
}
