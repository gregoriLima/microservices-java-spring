package com.javams.hroauth.controllers;

import java.util.ArrayList;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.javams.hroauth.entities.TokenDTO;
import com.javams.hroauth.form.LoginForm;
import com.javams.hroauth.services.TokenService;

@RestController
@RequestMapping("/auth")
public class AutenticacaoController {

	@Autowired 
	private AuthenticationManager authManager;

	@Autowired
	private TokenService tokenService;

	
	@GetMapping
	public String ola() {
		return "Olá auth";
	}
	
	@PostMapping
	public ResponseEntity<TokenDTO> autenticar(@RequestBody @Valid LoginForm form) { 
	
		UsernamePasswordAuthenticationToken dadosLogin = form.converter(); 

		try {

			Authentication authenticate = authManager.authenticate(dadosLogin); 

			String token = tokenService.gerarToken(authenticate, dadosLogin.getName()); 

			return ResponseEntity.ok(new TokenDTO(token, "Bearer"));
			
		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("Erro ao autenticar");
			return ResponseEntity.badRequest().build();
		}

	}

}
