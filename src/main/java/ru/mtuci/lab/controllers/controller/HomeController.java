package ru.mtuci.lab.controllers.controller;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, Object> home() {
        return Map.of(
                "message", "Spring Boot controllers lab",
                "time", LocalDateTime.now(),
                "endpoints", new String[] {"/api/hello", "/api/students", "/api/students/{id}"}
        );
    }
}
