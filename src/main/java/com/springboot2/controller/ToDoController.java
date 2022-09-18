package com.springboot2.controller;

import com.springboot2.domain.ToDo;
import com.springboot2.repository.ToDoRepository;
import com.springboot2.util.ToDoValidationError;
import com.springboot2.util.ToDoValidationErrorBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ToDoController {
    private final ToDoRepository repository;

    @GetMapping(value = "/to-do", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<Iterable<ToDo>> getToDos(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<>(repository.findAll(), headers, HttpStatus.OK);
    }

    @GetMapping(value = "/to-do/{toDoId}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<?> getToDo(@PathVariable String toDoId) {
        Optional<ToDo> existingToDo = repository.findById(toDoId);

        if (existingToDo.isEmpty()) {
            return new ResponseEntity<>("To do with id" + toDoId + " does not exist", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(repository.findById(toDoId).get(), HttpStatus.OK);
    }

    @DeleteMapping(value = "/to-do/{toDoId}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<?> deleteToDo(@PathVariable String toDoId) {
        Optional<ToDo> existingToDo = repository.findById(toDoId);

        if (existingToDo.isEmpty()) {
            return new ResponseEntity<>("To do with id" + toDoId + " does not exist", HttpStatus.NOT_FOUND);
        }

        repository.delete(existingToDo.get());
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/to-do", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ToDo> createToDo(@Valid @RequestBody ToDo toDo) {
        return new ResponseEntity<>(repository.save(toDo), HttpStatus.OK);
    }

    @PutMapping(value = "/to-do", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<ToDo> updateToDo(@Valid @RequestBody ToDo toDo) {
        return new ResponseEntity<>(repository.save(toDo), HttpStatus.OK);
    }

    @PatchMapping(value = "complete-to-do/{toDoId}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<?> completeToDO(@PathVariable String toDoId) {
        Optional<ToDo> existingToDo = repository.findById(toDoId);

        if (existingToDo.isEmpty()) {
            return new ResponseEntity<>("Such to do does not exist", HttpStatus.NOT_FOUND);
        }

        existingToDo.get().setCompleted(true);
        return new ResponseEntity<>(repository.save(existingToDo.get()), HttpStatus.OK);
    }

    @ExceptionHandler
    @ResponseStatus(value=HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception e) {
        return new ToDoValidationError(e.getMessage());
    }
}
