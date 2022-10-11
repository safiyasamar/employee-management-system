package com.demo.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

}
