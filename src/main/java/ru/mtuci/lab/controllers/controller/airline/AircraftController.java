package ru.mtuci.lab.controllers.controller.airline;

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
import ru.mtuci.lab.controllers.dto.airline.AircraftRequest;
import ru.mtuci.lab.controllers.dto.airline.AircraftResponse;

@RestController
@RequestMapping("/api/aircraft")
public class AircraftController {

    private final List<AircraftResponse> aircraft = new ArrayList<>(List.of(
            new AircraftResponse(1L, "Airbus A320", "RA-73001", 180),
            new AircraftResponse(2L, "Boeing 737-800", "RA-73102", 189)
    ));

    @GetMapping
    public List<AircraftResponse> findAll() {
        return aircraft;
    }

    @GetMapping("/{id}")
    public AircraftResponse findById(@PathVariable long id) {
        return findAircraft(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AircraftResponse create(@Valid @RequestBody AircraftRequest request) {
        AircraftResponse response = new AircraftResponse(nextId(), request.model(), request.tailNumber(), request.seats());
        aircraft.add(response);
        return response;
    }

    @PutMapping("/{id}")
    public AircraftResponse update(@PathVariable long id, @Valid @RequestBody AircraftRequest request) {
        int index = findIndex(id);
        AircraftResponse response = new AircraftResponse(id, request.model(), request.tailNumber(), request.seats());
        aircraft.set(index, response);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        aircraft.remove(findIndex(id));
    }

    private AircraftResponse findAircraft(long id) {
        return aircraft.stream()
                .filter(item -> item.id() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Aircraft", id));
    }

    private int findIndex(long id) {
        for (int index = 0; index < aircraft.size(); index++) {
            if (aircraft.get(index).id() == id) {
                return index;
            }
        }
        throw new ResourceNotFoundException("Aircraft", id);
    }

    private long nextId() {
        return aircraft.stream().mapToLong(AircraftResponse::id).max().orElse(0L) + 1L;
    }
}
