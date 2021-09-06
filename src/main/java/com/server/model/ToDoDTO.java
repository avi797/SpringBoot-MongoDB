package com.server.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection="todos")
public class ToDoDTO {
	

	@Id
	private String id;
	
	@NotNull(message="todo cannot be null")
	private String todo;
	
	@NotNull(message="description cannot be null")
	private String description;
	
	@NotNull(message="completed cannot be null")
	private boolean completed;
	
	private Date createdAt;
	
	private Date updatedAt;
	

	

}
