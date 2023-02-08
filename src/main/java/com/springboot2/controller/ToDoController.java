package com.springboot2.controller;

import com.springboot2.domain.ToDo;
import com.springboot2.repository.ToDoRepository;
import com.springboot2.util.ToDoValidationError;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ToDoController {
    private final ToDoRepository repository;

    @GetMapping(value = "/to-do", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<Flux<ToDo>> getToDos() {
        return ResponseEntity.ok()
                .body(repository.findAll());
    }

    @GetMapping(value = "/to-do/{toDoId}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<Mono<ToDo>> getToDo(@Valid @PathVariable Long toDoId) {
        return ResponseEntity.ok()
                .body(repository.findById(toDoId));
    }

    @DeleteMapping(value = "/to-do/{toDoId}", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public Mono<ResponseEntity<Void>> deleteToDo(@PathVariable Long toDoId) {
        return repository.deleteById(toDoId)
                .map(result -> ResponseEntity.ok().<Void>build());
    }

    @PostMapping(value = "/to-do", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<Mono<ToDo>> createToDo(@Valid @RequestBody ToDo toDo) {
        return ResponseEntity.ok()
                .body(repository.save(toDo));
    }

    @PutMapping(value = "/to-do", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<Mono<ToDo>> updateToDo(@Valid @RequestBody ToDo toDo) {
        return ResponseEntity.ok(repository.findById(toDo.getId())
                .map(todo -> {
                    todo.setDescription(toDo.getDescription());
                    todo.setCompleted(toDo.getCompleted());
                    return todo;
                })
                .flatMap(repository::save));
    }

    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception e) {
        return new ToDoValidationError(e.getMessage());
    }
}
