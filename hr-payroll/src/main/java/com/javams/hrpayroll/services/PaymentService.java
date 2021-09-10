package com.javams.hrpayroll.services;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.javams.hrpayroll.entities.Payment;
import com.javams.hrpayroll.entities.Worker;
import com.javams.hrpayroll.feignclients.WorkerFeignClient;

//@Component // registrando como um componente do Spring para que possa ser
			// injetado em outras classes
@Service
public class PaymentService {

		
	
		// ***
		// Usando RestTemplate
		// @Value("${hr-worker.host}") 
		private String workerHost;
		
		@Autowired
		private RestTemplate restTemplate;
			
		public Payment getRestPayment(Long workerId, Integer days) {
			
			// fazendo chamada para o ws de workers
			
			Map<String, String> uriVariables = new HashMap<>();
			uriVariables.put("id", Long.toString(workerId));
					
			Worker worker = restTemplate.getForObject(workerHost+"/workers/{id}", Worker.class, uriVariables);
			
			return new Payment(worker.getName(), worker.getDailyMoneyIncome(), days);
		}
		
		// ***
		
		
		
		// ***
		// Usando Feign
		@Autowired
		private WorkerFeignClient workerFeignClient;
	
		public Payment getFeignPayment(Long workerId, Integer days) {
			
								
			// dessa maneira não funciona, pois lá na assinatura da interface,
			// o que é devolvido é um ResponseEntity<Worker>
			//Worker worker = workerFeignClient.findWorkerById(workerId);
			
			// o getBody() pega o Worker retornado pelo ResponseEntity
 			Worker worker = workerFeignClient.findWorkerById(workerId).getBody();

			return new Payment(worker.getName(), worker.getDailyMoneyIncome(), days);
		}
	
}
