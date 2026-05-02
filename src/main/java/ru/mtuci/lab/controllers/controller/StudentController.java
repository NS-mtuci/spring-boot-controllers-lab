package ru.mtuci.lab.controllers.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import ru.mtuci.lab.controllers.dto.CreateStudentRequest;
import ru.mtuci.lab.controllers.dto.StudentResponse;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final List<StudentResponse> students = new ArrayList<>(List.of(
            new StudentResponse(1L, "Ivan Petrov", "IVBO-01-25"),
            new StudentResponse(2L, "Anna Sidorova", "IVBO-02-25")
    ));

    @GetMapping
    public List<StudentResponse> findAll() {
        return students;
    }

    @GetMapping("/{id}")
    public StudentResponse findById(@PathVariable long id) {
        return students.stream()
                .filter(student -> student.id() == id)
                .findFirst()
                .orElseThrow(() -> new StudentNotFoundException(id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponse create(@Valid @RequestBody CreateStudentRequest request) {
        StudentResponse student = new StudentResponse(
                students.size() + 1L,
                request.fullName(),
                request.groupName()
        );
        students.add(student);
        return student;
    }
}
