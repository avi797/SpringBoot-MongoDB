package com.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.server.exception.ToDoCollectionException;
import com.server.model.ToDoDTO;
import com.server.repository.ToDoRepository;

@Service
public class ToDoserviceImpl implements ToDoService {

	@Autowired
	private ToDoRepository todoRepo;
	
	@Override
	public void createTodo(ToDoDTO todo) throws ToDoCollectionException, ConstraintViolationException {
		Optional<ToDoDTO> todoOptional= todoRepo.findByTodo(todo.getTodo());
		if(todoOptional.isPresent()) {
			throw new ToDoCollectionException(ToDoCollectionException.ToDoAlreadyExists());
		}else {
			todo.setCreatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todo);
		}
		
	}

	@Override
	public List<ToDoDTO> getAllTodos() {
		List<ToDoDTO> todos=todoRepo.findAll();
		if(todos.size()>0) {
			return todos;
		}else {
			return new ArrayList<ToDoDTO>();
		}
	}

	@Override
	public ToDoDTO getSingleTodo(String id) throws ToDoCollectionException {
		Optional<ToDoDTO> optionalTodo=todoRepo.findById(id);
		if(!optionalTodo.isPresent()) {
			throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
		}else {
			return optionalTodo.get();
		}
	}

	@Override
	public void updateTodo(String id, ToDoDTO todo) throws ToDoCollectionException {
		Optional<ToDoDTO> todoWithId=todoRepo.findById(id);
		Optional<ToDoDTO> todoWithSameName = todoRepo.findByTodo(todo.getTodo());
		if(todoWithId.isPresent()) {
			
			if(todoWithSameName.isPresent() && todoWithSameName.get().getId().equals(id)) {
				throw new ToDoCollectionException(ToDoCollectionException.ToDoAlreadyExists());
				
			}
			
			ToDoDTO todoToUpdate = todoWithId.get();
			
			todoToUpdate.setTodo(todo.getTodo());
			todoToUpdate.setDescription(todo.getDescription());
			todoToUpdate.setCompleted(todo.isCompleted());
			todoToUpdate.setUpdatedAt(new Date(System.currentTimeMillis()));
			todoRepo.save(todoToUpdate);
			
			
			
		}else {
			throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
		}
	}

	@Override
	public void deleteToDoByID(String id) throws ToDoCollectionException {
		Optional<ToDoDTO> todoOptional=todoRepo.findById(id);
		if(!todoOptional.isPresent()) {
			throw new ToDoCollectionException(ToDoCollectionException.NotFoundException(id));
		}else {
			todoRepo.deleteById(id);
		}
	}

	
}
