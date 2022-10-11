package com.demo.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer>{
	
	
}
