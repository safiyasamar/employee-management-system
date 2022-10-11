package com.demo.spring.rest;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.EmployeeDto;
import com.demo.spring.repo.EmployeeRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("a")
@Timed(value = "adminEmp_app")
public class AdminEmpRestController {
	@Autowired
	private EmployeeRepository erepo;
	
	static Logger logger=LoggerFactory.getLogger(AdminEmpRestController.class);
	
	
	//----------------------------- add new Employee ------------------------------
	
	@PostMapping(path = "/employee/add",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addTimesheet(@RequestBody EmployeeDto e) {
		logger.info("Adding new employee");
		if (erepo.existsById(e.getEmpNo())) {
			logger.error("Employee already exists");
			return ResponseEntity.ok("{\"status\":\"Employee Already Exists\"}");
		} else {
			Employee emp=new Employee();
			emp.setEmpNo(e.getEmpNo());
			emp.setName(e.getName());
			emp.setCity(e.getCity());
			emp.setSalary(e.getSalary());
			erepo.save(emp);
			logger.info("Returning success response for employee");
			return ResponseEntity.ok("{\"status\":\"Employee Added Successfully\"}");
		}
	}
	
	
	
	
	
	//-------------------------------- View Profile of the Employee ----------------------------
	
	@GetMapping(path = "/employee/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> getEmployeeDetails(@PathVariable int id) {
		logger.info("Getting data for employee with id "+id);
		Optional<Employee> empOp=erepo.findById(id);
		if(empOp.isPresent()) {
			logger.info("Returning response for employee with id "+id);
			return ResponseEntity.ok(empOp.get());
		}else {
			logger.error("Employee does not exist with id "+id);
			return ResponseEntity.ok("{\"status\":\"Employee does not exist\"}");
		}
	}
	
	
	
	
	
	//----------------------------------- Listing of all Employees -------------------------------
	@GetMapping(path = "/employee/list",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Employee>> getAllEmployee() {
		logger.error("Returning Employee List");
		return ResponseEntity.ok(erepo.findAll());
	}
	
}
