package com.javams.hroauth.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javams.hroauth.entities.User;
import com.javams.hroauth.services.TokenService;

public class JwtTokenFilter extends OncePerRequestFilter{
	
	 private TokenService tokenService;
	    
	 private UserDetailsService userService;
	 
	 
	 public JwtTokenFilter(TokenService tokenService, UserDetailsService userService) {

	        this.tokenService = tokenService;
	        this.userService = userService;
	    }
	 

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// recuperando o token do cabeçalho
		String token = tokenRecovery(request);
		
		// verificando se o token enviado é válido
		boolean tokenValido = tokenService.isTokenValido(token);
		
		if (tokenValido) { 
		    autenticarCliente(token);
		    // Get user identity and set it on the spring security context
	        UserDetails userDetails = userService
	            .loadUserByUsername(tokenService.getEmaildoUsuarioViaToken(token));
		}
		
		filterChain.doFilter(request, response); // seguindo o fluxo da requisição
		
		
	}
	
	
	private String tokenRecovery(HttpServletRequest request) {
		   
	     String token = request.getHeader(HttpHeaders.AUTHORIZATION);
	     
	     // verificando se o cabeçalho está correto:
	     if (token == null || token.isEmpty() || !token.startsWith("Bearer "))
		     return null;
	     
	     return token.split(" ")[1]; // devolvendo só o token
	}
	
	
	  // este método força uma autenticação do usuário, pois o token foi válidado já
    private void autenticarCliente(String token) {
			
			String emailUsuario = tokenService.getEmaildoUsuarioViaToken(token);

			User usuario = (User) userService.loadUserByUsername(emailUsuario);
			
			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
			
			SecurityContextHolder.getContext().setAuthentication(authentication);
			
    	}

}
