package com.server.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.server.exception.ToDoCollectionException;
import com.server.model.ToDoDTO;
import com.server.repository.ToDoRepository;
import com.server.service.ToDoService;

@RestController
public class ToDoController {
	

	@Autowired
	private ToDoService todoService;
	
	@Autowired
	private ToDoRepository todoRepo;
	
	@GetMapping("/todos")
	public ResponseEntity<?> getAllTodos(){
		List<ToDoDTO> todos=todoService.getAllTodos();
		return new ResponseEntity<>(todos, todos.size()>0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}
	
	@PostMapping("/todos")
	public ResponseEntity<?> createTodo(@RequestBody ToDoDTO todo){
		try {
			todoService.createTodo(todo);
		return new ResponseEntity<ToDoDTO>(todo, HttpStatus.OK );
		}catch(ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(ToDoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
		
	}
	
	@GetMapping("/todos/{id}")
	public ResponseEntity<?> getSingleToDo(@PathVariable("id") String id){
		try {
			return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
			
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@PutMapping("/todos/{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody ToDoDTO todo){
		Optional<ToDoDTO> todoOptional = todoRepo.findById(id);
		try {
			todoService.updateTodo(id, todo);
			return new ResponseEntity<>("update todo with id " + id, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		}catch(ToDoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
	}
	
	@DeleteMapping("/todos/{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id){
		try {
			todoService.deleteToDoByID(id);
			return new ResponseEntity<>("successfully deleted by id " + id, HttpStatus.OK);
			
		} catch (ToDoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	
	
	
	
	
	
	
}
