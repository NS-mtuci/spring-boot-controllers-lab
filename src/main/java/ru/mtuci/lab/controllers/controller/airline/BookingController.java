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
import ru.mtuci.lab.controllers.dto.airline.BookingRequest;
import ru.mtuci.lab.controllers.dto.airline.BookingResponse;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final List<BookingResponse> bookings = new ArrayList<>(List.of(
            new BookingResponse(1L, "Ivan Petrov", 1L, "12A", "CONFIRMED"),
            new BookingResponse(2L, "Anna Sidorova", 2L, "8C", "CHECKED_IN")
    ));

    @GetMapping
    public List<BookingResponse> findAll() {
        return bookings;
    }

    @GetMapping("/{id}")
    public BookingResponse findById(@PathVariable long id) {
        return findBooking(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookingResponse create(@Valid @RequestBody BookingRequest request) {
        BookingResponse response = new BookingResponse(
                nextId(),
                request.passengerName(),
                request.flightId(),
                request.seatNumber(),
                request.status()
        );
        bookings.add(response);
        return response;
    }

    @PutMapping("/{id}")
    public BookingResponse update(@PathVariable long id, @Valid @RequestBody BookingRequest request) {
        int index = findIndex(id);
        BookingResponse response = new BookingResponse(
                id,
                request.passengerName(),
                request.flightId(),
                request.seatNumber(),
                request.status()
        );
        bookings.set(index, response);
        return response;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable long id) {
        bookings.remove(findIndex(id));
    }

    private BookingResponse findBooking(long id) {
        return bookings.stream()
                .filter(item -> item.id() == id)
                .findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Booking", id));
    }

    private int findIndex(long id) {
        for (int index = 0; index < bookings.size(); index++) {
            if (bookings.get(index).id() == id) {
                return index;
            }
        }
        throw new ResourceNotFoundException("Booking", id);
    }

    private long nextId() {
        return bookings.stream().mapToLong(BookingResponse::id).max().orElse(0L) + 1L;
    }
}
