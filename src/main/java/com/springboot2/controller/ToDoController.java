package com.springboot2.controller;

import com.springboot2.domain.ToDo;
import com.springboot2.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class ToDoController {
    private final ToDoRepository repository;

    @GetMapping(value = "/to_do_list", produces = {
            MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_XML_VALUE})
    public ResponseEntity<Iterable<ToDo>> getToDos(@RequestHeader HttpHeaders headers) {
        return new ResponseEntity<Iterable<ToDo>>(this.repository.findAll(), headers, HttpStatus.OK);
    }
}
