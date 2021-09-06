package com.server.repository;


import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.server.model.ToDoDTO;



@Repository
public interface ToDoRepository extends MongoRepository<ToDoDTO, String> {

	@Query("{'todo' : ?0}")
	Optional<ToDoDTO> findByTodo(String todo);
}
