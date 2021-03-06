package com.javams.hrpayroll.controllers;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javams.hrpayroll.entities.Payment;
import com.javams.hrpayroll.services.PaymentService;

import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping(value = "/payments")
public class PaymentController {

	
	// ***
	// usando RestTemplate
	@Autowired
	private PaymentService service;
	
	
	@GetMapping
	public String hello() {
		return "hello payment";
	}
	
	/* old dependency
	 	<dependency>
			<groupId>org.springframework.cloud</groupId>
			<artifactId>spring-cloud-starter-circuitbreaker-resilience4j</artifactId>
		</dependency>
	 */
	
	// https://resilience4j.readme.io/docs/getting-started-3
	// Bulkhead module is added because TimeLimiter needs separate execution thread instead of request thread
	@Bulkhead(name = "paymentCalc", fallbackMethod = "localPaymentGenerate", type = Bulkhead.Type.THREADPOOL)
	@TimeLimiter(name = "paymentCalc", fallbackMethod = "localPaymentGenerate")
	//@Retry(name="paymentCalc", fallbackMethod = "localPaymentGenerate")
	@GetMapping(value = "/{workerId}/days/{days}")
	public CompletableFuture<ResponseEntity<Payment>> getPayment(@PathVariable Long workerId, @PathVariable Integer days){
		
		// foi trocado o RestTemplate pelo FeingClient
		//Payment payment = service.getRestPayment(workerId, days);
		
		Payment payment = service.getFeignPayment(workerId, days);
		
		return CompletableFuture.completedFuture(ResponseEntity.ok(payment));
		
	}
	// ***
	
	public CompletableFuture<ResponseEntity<Payment>> localPaymentGenerate(Long workerId, Integer days, Exception e){
			
			System.out.println("failure");
			System.out.println(e.getMessage()); // TimeLimiter 'paymentCalc' recorded a timeout exception.
		
			Payment localPayment = new Payment("Falhonildo", 13.0, 13);
			
			return CompletableFuture.completedFuture(ResponseEntity.ok(localPayment));
	}
	
	
	
}
