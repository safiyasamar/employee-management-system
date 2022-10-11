package com.demo.spring.rest;

import java.util.List;

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

import com.demo.spring.entity.Leave;
import com.demo.spring.entity.LeaveDto;
import com.demo.spring.repo.LeaveRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("e")
@Timed(value = "empLeave_app")
public class LeaveRestController {
	
	@Autowired
	LeaveRepository lrepo;
	
	static Logger logger=LoggerFactory.getLogger(LeaveRestController.class);
	
	
	//-------------------------------- apply leave ----------------------------
	
	@PostMapping(path = "/leave/apply", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> applyLeave(@RequestBody LeaveDto l) {
		logger.info("Apply leave for Employee");
		if (lrepo.existsById(l.getLeaveId())) {
			logger.error("leave can not be added for the employee");
			return ResponseEntity.ok("{\"status\":\"Something went wrong\"}");
		} else {
			Leave leave=new Leave();
			leave.setEmpNo(l.getEmpNo());
			leave.setLeaveId(l.getLeaveId());
			leave.setNoOfDays(l.getNoOfDays());
			leave.setReason(l.getReason());
			leave.setStatus(l.getStatus());
			lrepo.save(leave);
			logger.info("Successfully added leave for the employee");
			return ResponseEntity.ok("{\"status\":\"Leave Applied By Employee\"}");
		}
		
	}
	
	
	
	//--------------------------------- view leave records --------------------------------
	
	@GetMapping(path = "/leave/list/{empno}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Leave>> getLeaveList(@PathVariable("empno") int empNo) {
		logger.info("Getting data for all the leaves applied by the employee with id "+empNo);
		return ResponseEntity.ok(lrepo.findAllByEmpNo(empNo));
	}
	
}
