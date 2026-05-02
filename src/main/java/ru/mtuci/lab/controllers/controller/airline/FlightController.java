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

import ru.mtuci.lab.controllers.dto.airline.FlightRequest;
import ru.mtuci.lab.controllers.dto.airline.FlightResponse;
import ru.mtuci.lab.controllers.service.FlightService;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<FlightResponse> findAll() {
        return flightService.findAll();
    }

    @GetMapping("/{id}")
    public FlightResponse findById(@PathVariable long id) {
        return flightService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse create(@Valid @RequestBody FlightRequest request) {
        return flightService.create(request);
    }

    @PutMapping("/{id}")
    public FlightResponse update(@PathVariable long id, @Valid @RequestBody FlightRequest request) {
        return flightService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        flightService.delete(id);
    }
}
