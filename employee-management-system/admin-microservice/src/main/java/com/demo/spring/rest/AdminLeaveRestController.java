package com.demo.spring.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Leave;
import com.demo.spring.entity.LeaveDto;
import com.demo.spring.repo.LeaveRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("a")
@Timed(value = "adminLeave_app")
public class AdminLeaveRestController {

	@Autowired
	LeaveRepository lrepo;
	
	static Logger logger=LoggerFactory.getLogger(AdminLeaveRestController.class);
	
	
	

	//----------------------------------- Add Leave for the Employee ----------------------------
	
	@PostMapping(path = "/leave/apply", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> applyLeave(@RequestBody LeaveDto l) {
		logger.info("Applying leave for employee");
		if (lrepo.existsById(l.getLeaveId())) {
			logger.error("Cannot add leave for employee");
			return ResponseEntity.ok("{\"status\":\"Something went wrong\"}");
		} else {
			Leave leave=new Leave();
			leave.setEmpNo(l.getEmpNo());
			leave.setLeaveId(l.getLeaveId());
			leave.setNoOfDays(l.getNoOfDays());
			leave.setReason(l.getReason());
			leave.setStatus(l.getStatus());
			lrepo.save(leave);
			logger.info("Successfully added leave for employee");
			return ResponseEntity.ok("{\"status\":\"Leave Applied By Employee\"}");
		}
	}
	
	
	
	

	//------------------------------- Listing of the Leave for all Employees --------------------------
	
	@GetMapping(path = "/leave/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Leave>> getAllLeave() {
		logger.info("Getting data for all the leave applied by employees");
		return ResponseEntity.ok(lrepo.findAll());
	}
	
	
	
	

	//-------------------------------- Approve/Disapprove Leave ---------------------------------
	
	@PutMapping(path = "/leave/status", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> changeStatus(@RequestBody LeaveDto l) {
		logger.info("Editing leave status for employee");
		if (lrepo.existsById(l.getLeaveId())) {
			Leave leave=new Leave();
			leave.setEmpNo(l.getEmpNo());
			leave.setLeaveId(l.getLeaveId());
			leave.setNoOfDays(l.getNoOfDays());
			leave.setReason(l.getReason());
			leave.setStatus(l.getStatus());
			lrepo.save(leave);
			logger.info("Successfully changes the status of leave for a employee");
			return ResponseEntity.ok("{\"status\":\"Status Changed Successfully\"}");
		} else {
			logger.error("No such leave has been applied by employee");
			return ResponseEntity.ok("{\"status\":\"No such leave exists\"}");
		}
	}

}
