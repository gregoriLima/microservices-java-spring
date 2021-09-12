package com.javams.hroauth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.javams.hroauth.entities.User;
import com.javams.hroauth.feignClients.UserFeignClients;

@Service
public class UserService implements UserDetailsService {
	
	private static Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserFeignClients userFeignClient;
	
	
	// para testes
	public User finByEmail(String email) {
		
		User user = userFeignClient.findWorkerByEmail(email).getBody();
		
		if (user == null) {
			logger.error("Email not found: " + email);
			//throw new IllegalArgumentException("Email not found");
		}
		
		logger.info("Email found: " + email);
		
		return user;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		User user = userFeignClient.findWorkerByEmail(email).getBody();
			
			if (user == null) {
				logger.error("User not found: " + email);
				throw new UsernameNotFoundException("User not found");
			}
			
			logger.info("User found: " + email);
			  
			return user;
		
	}

}
