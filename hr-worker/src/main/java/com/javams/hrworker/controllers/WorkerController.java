package com.javams.hrworker.controllers;

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
import org.springframework.web.bind.annotation.RestController;

import com.javams.hrworker.entities.Worker;
import com.javams.hrworker.repositories.WorkRepository;

@RefreshScope
@RestController
@RequestMapping(value = "/workers")
public class WorkerController {

	private static Logger log = org.slf4j.LoggerFactory.getLogger(WorkerController.class);
	
	// configuração vinda do server config que busca a configuração do git repository
	//@Value("${test.config}")
	private String testConfig;
	
	@Autowired
	private Environment env;
	
	@Autowired
	private WorkRepository repository;
	

	
	
	@GetMapping
	public ResponseEntity<List<Worker>> findAllWorkers(){
		
		List<Worker> listOfWorkers = repository.findAll();
		
		return ResponseEntity.ok(listOfWorkers);
		
	}
	
	
	
	@GetMapping(value="/configs")
	public ResponseEntity<Void> getConfigs(){

		log.info("Config = " + testConfig);
		
		return ResponseEntity.noContent().build();
		
	}
	
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<Worker> findWorkerById(@PathVariable Long id){
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// System.out.println("acessado servidor pela porta " + env.getProperty("local.server.port"));
		
		Worker worker = repository.findById(id).get();
		
		return ResponseEntity.ok(worker);
		
	}
	
}
