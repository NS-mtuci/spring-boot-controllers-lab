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

import ru.mtuci.lab.controllers.dto.airline.PassengerRequest;
import ru.mtuci.lab.controllers.dto.airline.PassengerResponse;
import ru.mtuci.lab.controllers.service.PassengerService;

@RestController
@RequestMapping("/api/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    public PassengerController(PassengerService passengerService) {
        this.passengerService = passengerService;
    }

    @GetMapping
    public List<PassengerResponse> findAll() {
        return passengerService.findAll();
    }

    @GetMapping("/{id}")
    public PassengerResponse findById(@PathVariable long id) {
        return passengerService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PassengerResponse create(@Valid @RequestBody PassengerRequest request) {
        return passengerService.create(request);
    }

    @PutMapping("/{id}")
    public PassengerResponse update(@PathVariable long id, @Valid @RequestBody PassengerRequest request) {
        return passengerService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        passengerService.delete(id);
    }
}
