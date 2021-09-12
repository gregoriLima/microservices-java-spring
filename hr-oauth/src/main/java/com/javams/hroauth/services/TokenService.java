package com.javams.hroauth.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.javams.hroauth.entities.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

    @Value(value = "${hr-oauth.jwt.expiration}")
    private String expiration;
            
    @Value("${hr-oauth.jwt.secret}")
    private String secret;
    
    @Autowired
    private UserService userService;
    
    public String gerarToken(Authentication authenticate, String email) {
	
		User usuario = (User) userService.loadUserByUsername(email);
		String emailDoUsuario = null;
	
		if(usuario != null)
			emailDoUsuario = usuario.getEmail();
		
		Date hoje = new Date();
		
		Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));
		
		System.out.println("expiration: " + dataExpiracao);
		
		return Jwts.builder()
			.setIssuer("hr-oauth")
			.setSubject(emailDoUsuario.toString())
			.setIssuedAt(hoje)
			.setExpiration(dataExpiracao)
			.signWith(SignatureAlgorithm.HS256, secret)
			.compact();
    }

    public boolean isTokenValido(String token) {
	
		try {
			Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
    }

    
    public String getEmaildoUsuarioViaToken(String token) {
    	Claims claims = Jwts.parser().setSigningKey(this.secret).parseClaimsJws(token).getBody();
    	return claims.getSubject();
    }

    
    
}
