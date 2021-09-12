package com.javams.hruser.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javams.hruser.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByEmail(String email);
	
}
