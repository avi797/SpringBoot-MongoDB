package com.server.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.server.exception.ToDoCollectionException;
import com.server.model.ToDoDTO;

public interface ToDoService {
	
	public void createTodo(ToDoDTO todo) throws ToDoCollectionException, ConstraintViolationException;

	public List<ToDoDTO> getAllTodos();
	
	public ToDoDTO getSingleTodo(String id) throws ToDoCollectionException;
	
	public void updateTodo(String id, ToDoDTO todo) throws ToDoCollectionException;
	
	public void deleteToDoByID(String id) throws ToDoCollectionException;
}
