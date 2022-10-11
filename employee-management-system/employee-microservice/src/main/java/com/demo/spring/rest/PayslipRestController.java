package com.demo.spring.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Payslip;
import com.demo.spring.repo.PayslipRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("e")
@Timed(value = "empPayslip_app")
public class PayslipRestController {
	
	@Autowired
	PayslipRepository prepo;
	
	static Logger logger=LoggerFactory.getLogger(PayslipRestController.class);
	
	
	//--------------------------------- view payslip ----------------------------------
	
	@GetMapping(path = "/payslip/list/{empno}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Payslip>> getPayslipList(@PathVariable("empno") int empNo) {
		logger.info("Getting data od payslip for employee with id "+empNo);
		return ResponseEntity.ok(prepo.findAllByEmpNo(empNo));
	}
	

}
