package com.javams.hruser.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.javams.hruser.entities.User;
import com.javams.hruser.repositories.UserRepository;

@RestController
@RequestMapping(value = "/users")
public class UserController {

	@Autowired
	private UserRepository repository;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<User> findWorkerByEmail(@PathVariable Long id){
	
		User user = repository.findById(id).get();
		
		return ResponseEntity.ok(user);
		
	}
	
	
	@GetMapping(value = "/search")
	public ResponseEntity<User> findWorkerByEmail(@RequestParam String email ){
	
		User user = repository.findByEmail(email);
				
		return ResponseEntity.ok(user);
		
	}
	
}