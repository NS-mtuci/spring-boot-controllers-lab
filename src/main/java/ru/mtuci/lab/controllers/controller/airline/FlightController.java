package ru.mtuci.lab.controllers.controller.airline;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import ru.mtuci.lab.controllers.controller.ResourceNotFoundException;
import ru.mtuci.lab.controllers.dto.airline.FlightRequest;
import ru.mtuci.lab.controllers.dto.airline.FlightResponse;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final List<FlightResponse> flights = new ArrayList<>(List.of(
            new FlightResponse(1L, "SU100", "Moscow", "Saint Petersburg", LocalDateTime.now().plusDays(3), 1L, "SCHEDULED"),
            new FlightResponse(2L, "SU220", "Moscow", "Kazan", LocalDateTime.now().plusDays(5), 2L, "SCHEDULED")
    ));

    @GetMapping
    public List<FlightResponse> findAll() {
        return flights;
    }

    @GetMapping("/{id}")
    public FlightResponse findById(@PathVariable long id) {
        return findFlight(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FlightResponse create(@Valid @RequestBody FlightRequest request) {
        FlightResponse response = toResponse(nextId(), request);
        flights.add(response);
        return response;
    }

    @PutMapping("/{id}")
    public FlightResponse update(@PathVariable long id, @Valid @RequestBody FlightRequest request) {
        int index = findIndex(id);
        FlightResponse response = toResponse(id, request);
        flights.set(index, response);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        flights.remove(findIndex(id));
    }

    private FlightResponse toResponse(long id, FlightRequest request) {
        return new FlightResponse(
                id,
                request.flightNumber(),
                request.departureCity(),
                request.arrivalCity(),
                request.departureTime(),
                request.aircraftId(),
                request.status()
        );
    }

    private FlightResponse findFlight(long id) {
        return flights.stream()
                .filter(item -> item.id() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Flight", id));
    }

    private int findIndex(long id) {
        for (int index = 0; index < flights.size(); index++) {
            if (flights.get(index).id() == id) {
                return index;
            }
        }
        throw new ResourceNotFoundException("Flight", id);
    }

    private long nextId() {
        return flights.stream().mapToLong(FlightResponse::id).max().orElse(0L) + 1L;
    }
}
