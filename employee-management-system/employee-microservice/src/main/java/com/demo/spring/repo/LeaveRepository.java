package com.demo.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Leave;

public interface LeaveRepository extends JpaRepository<Leave, Integer>{
	public List<Leave> findAllByEmpNo(int empNo);
}
