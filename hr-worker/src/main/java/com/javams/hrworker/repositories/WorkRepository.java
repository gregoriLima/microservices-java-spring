package com.javams.hrworker.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javams.hrworker.entities.Worker;

public interface WorkRepository extends JpaRepository<Worker, Long>{

}
