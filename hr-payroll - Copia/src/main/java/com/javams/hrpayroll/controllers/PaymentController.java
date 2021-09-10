package com.javams.hrpayroll.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javams.hrpayroll.entities.Payment;
import com.javams.hrpayroll.services.PaymentService;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

	
	// ***
	// usando RestTemplate
	@Autowired
	private PaymentService service;
	
	@GetMapping(value = "/{workerId}/days/{days}")
	public ResponseEntity<Payment> getPayment(@PathVariable Long workerId, @PathVariable Integer days){
		
		// foi trocado o RestTemplate pelo FeingClient
		//Payment payment = service.getRestPayment(workerId, days);
		
		Payment payment = service.getFeignPayment(workerId, days);
		
		return ResponseEntity.ok(payment);
		
	}
	// ***
	
	
	
	
	
}
