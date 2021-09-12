package com.javams.hroauth.entities;

public class TokenDTO {

    private String tipo;
    private String token;

    public TokenDTO(String token, String tipo) {

	this.tipo = tipo;
	this.token = token;
	// TODO Auto-generated constructor stub
    }

    public String getTipo() {
        return tipo;
    }

    public String getToken() {
        return token;
    }

}
