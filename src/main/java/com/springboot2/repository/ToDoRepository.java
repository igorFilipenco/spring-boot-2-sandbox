package com.springboot2.repository;

import com.springboot2.domain.ToDo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToDoRepository extends ReactiveCrudRepository<ToDo,String> {
}
