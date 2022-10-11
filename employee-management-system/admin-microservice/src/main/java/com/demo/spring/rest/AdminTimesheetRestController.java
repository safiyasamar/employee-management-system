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

import com.demo.spring.entity.Timesheet;
import com.demo.spring.entity.TimesheetDto;
import com.demo.spring.repo.TimesheetRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("a")
@Timed(value = "adminTimesheet_app")
public class AdminTimesheetRestController {

	@Autowired
	TimesheetRepository trepo;
	
	
	static Logger logger=LoggerFactory.getLogger(AdminTimesheetRestController.class);
	
	

	//------------------------------- Add Timesheet for the Employee ----------------------------
	
	@PostMapping(path = "/timesheet/add", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addTimesheet(@RequestBody TimesheetDto t) {
		logger.info("Adding new timesheet for employee");
		if (trepo.existsById(t.getTno())) {
			logger.error("Cannot add timesheet for employee");
			return ResponseEntity.ok("{\"status\":\"Can't create this timesheet\"}");
		} else {
			Timesheet ts=new Timesheet();
			ts.setEmpNo(t.getEmpNo());
			ts.setTno(t.getTno());
			ts.setpId(t.getpId());
			ts.setpType(t.getpType());
			ts.setLocation(t.getLocation());
			ts.setTotalHours(t.getTotalHours());
			trepo.save(ts);
			logger.info("Successfully added timesheet for employee");
			return ResponseEntity.ok("{\"status\":\"Timesheet Added to the Employee\"}");
		}
	}
	
	
	
	

	//-------------------------------- Edit Timesheet for the Employee --------------------------
	
	@PutMapping(path = "/timesheet/edit", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editTimesheet(@RequestBody TimesheetDto t) {
		logger.info("Editing timesheet for employee");
		if (trepo.existsById(t.getTno())) {
			Timesheet ts=new Timesheet();
			ts.setEmpNo(t.getEmpNo());
			ts.setTno(t.getTno());
			ts.setpId(t.getpId());
			ts.setpType(t.getpType());
			ts.setLocation(t.getLocation());
			ts.setTotalHours(t.getTotalHours());
			trepo.save(ts);
			logger.info("Successfully edited the timesheet for employee");
			return ResponseEntity.ok("{\"status\":\"Timesheet edited for the employee\"}");
		} else {
			logger.error("This timesheet does not exist for employee");
			return ResponseEntity.ok("{\"status\":\"Timesheet does not exist\"}");
			
		}
	}
	
	
	
	

	//------------------------------ Listing of the Timesheet ---------------------------
	
	@GetMapping(path = "/timesheet/list", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Timesheet>> getAllTimesheet() {
		logger.info("Getting data for all the timesheet added for employees");
		return ResponseEntity.ok(trepo.findAll());
	}

}
