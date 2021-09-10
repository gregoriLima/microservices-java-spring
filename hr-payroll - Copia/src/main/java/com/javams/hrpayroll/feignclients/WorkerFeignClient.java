package com.javams.hrpayroll.feignclients;


import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@Component // É um componente gerenciado pelo Spring que pode ser injetado em outras classes
// abaixo está dizendo que somos um cliente de hr-worker, e a localização deste
// projeto que nós somos cliente é em localhost:8082
// name é o nome do projeto que é uma api. O trecho de código de 'url' foi comentado
// pois foi utilizado o load balancer, e depois não foi mais usado o load balancer
// pois foi usado o eureka, e o eureka já tem um load balancer
// neste caso está sendo encontrado o serviço somente pelo seu nome, que é hr-worker
// e está registrado no eureka
@FeignClient( name = "hr-worker", path = "/workers")//  url = "localhost:8082", path = "/workers") // hr-worker é o nome do microserviço lá no pom.xml
public interface WorkerFeignClient {
	
	// a assinatura desta interface é a mesma do controller de hr-worker, exatamente
	// a mesma, só um copia e cola
	@GetMapping(value = "/{id}")
	public ResponseEntity<com.javams.hrpayroll.entities.Worker> findWorkerById(@PathVariable Long id);
	// aqui está declarada o tipo de chamada WebService que será feita
	
}
