package com.demo.spring.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.EmployeeDto;
import com.demo.spring.repo.EmployeeRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("e")
@Timed(value = "emp_app")
public class EmployeeRestController {

	@Autowired
	private EmployeeRepository erepo;
	
	static Logger logger=LoggerFactory.getLogger(EmployeeRestController.class);
	

	//------------------------ edit employee details--------------------------------
	
	@PutMapping(path = "/employee", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateProfile(@RequestBody EmployeeDto e) {
		logger.info("Edit data for Employee");
		if (erepo.existsById(e.getEmpNo())) {
			Employee emp=new Employee();
			emp.setEmpNo(e.getEmpNo());
			emp.setName(e.getName());
			emp.setCity(e.getCity());
			emp.setSalary(e.getSalary());
			erepo.save(emp);
			logger.info("Successfully edited details for employee");
			return ResponseEntity.ok("{\"status\":\"Employee Updated Successfully\"}");
		} else {
			logger.error("No employee found with id "+e.getEmpNo());
			return ResponseEntity.ok("{\"status\":\"Employee doesn't exist\"}");
		}
	}

}
