package com.javams.hroauth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.javams.hroauth.services.TokenService;

@EnableWebSecurity
public class SecurityConfigurations {
	
	@Autowired
	private UserDetailsService userService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(userService).passwordEncoder(passwordEncode);
    }

    @Configuration
    @Order(1)
    public static class ApiWebSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {
        
    	@Override
 	    @Bean
 	    public AuthenticationManager authenticationManager() throws Exception {
 	        return super.authenticationManager();
 	    }
    	
    	// para pegar o token, o aplicativo deve estar autenticado também
    	protected void configure(HttpSecurity http) throws Exception {

    		http.antMatcher("/auth").authorizeRequests().anyRequest().hasRole("APP").and().httpBasic()
    		.and().csrf().disable() 
    		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    
    @Configuration
    public static class FormLoginWebSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {
   	
    	@Autowired
    	private TokenService tokenService;
    	
    	@Autowired
    	private UserDetailsService userService;
    	
    	// para todos os outros endpoints é necessário enviar o token tipo Bearer
        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	
        	http .authorizeRequests()
    		.anyRequest().authenticated()
      		.and().csrf().disable() 
    		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    		.and().addFilterBefore(new JwtTokenFilter(tokenService, userService), UsernamePasswordAuthenticationFilter.class);
    		
            
        }


    }
}


/*
@Configuration
@EnableWebSecurity
public class SecurityConfigurations extends WebSecurityConfigurerAdapter{

	    @Override
	    @Bean
	    public AuthenticationManager authenticationManager() throws Exception {
	        return super.authenticationManager();
	    }
	
	@Autowired
	private BCryptPasswordEncoder passwordEncode;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserDetailsService userService;
	
	
	@Override
	@Autowired
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncode);
	}
	
//	 @Autowired
//	   public void configure(AuthenticationManagerBuilder auth) throws Exception {
//	      auth
//	      .inMemoryAuthentication()
//	      .withUser("user")
//	      .password("$2a$10$NYFZ/8WaQ3Qb6FCs.00jce4nxX9w7AkgWVsQCG6oUwTAcZqP9Flqu")
//	      .roles("USER");
//	   }
	
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http .authorizeRequests()
		//.antMatchers(HttpMethod.POST, "/auth").permitAll()
  		.anyRequest().authenticated()
  		.and().httpBasic()
  		.and().csrf().disable() 
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		//.and().addFilterBefore(new AutenticacaoViaTokenFilter(tokenService, usuarioRepository), UsernamePasswordAuthenticationFilter.class);
		
		
	}
	
	

		

	
}
*/