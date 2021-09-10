package com.javams.hrworker.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javams.hrworker.entities.Worker;
import com.javams.hrworker.repositories.WorkRepository;

@RestController
@RequestMapping(value = "/workers")
public class WorkerController {

	@Autowired
	private Environment env;
	
	@Autowired
	private WorkRepository repository;
	
	@GetMapping
	public ResponseEntity<List<Worker>> findAllWorkers(){
		
		List<Worker> listOfWorkers = repository.findAll();
		
		return ResponseEntity.ok(listOfWorkers);
		
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Worker> findWorkerById(@PathVariable Long id){
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("acessado servidor pela porta " + env.getProperty("local.server.port"));
		
		Worker worker = repository.findById(id).get();
		
		return ResponseEntity.ok(worker);
		
	}
	
}
