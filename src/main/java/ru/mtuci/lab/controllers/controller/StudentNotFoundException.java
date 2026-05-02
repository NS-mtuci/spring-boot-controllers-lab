package ru.mtuci.lab.controllers.controller;

public class StudentNotFoundException extends ResourceNotFoundException {

    public StudentNotFoundException(long id) {
        super("Student", id);
    }
}
