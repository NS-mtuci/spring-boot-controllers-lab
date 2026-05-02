package ru.mtuci.lab.controllers.controller;

import java.util.Map;

import jakarta.validation.constraints.Min;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public Map<String, String> hello(@RequestParam(defaultValue = "student") String name) {
        return Map.of("message", "Hello, " + name + "!");
    }

    @GetMapping("/numbers/{number}")
    public Map<String, Object> numberInfo(@PathVariable @Min(1) int number) {
        return Map.of(
                "number", number,
                "square", number * number,
                "even", number % 2 == 0
        );
    }
}
