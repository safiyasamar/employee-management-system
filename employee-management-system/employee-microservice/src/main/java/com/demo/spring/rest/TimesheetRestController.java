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

import com.demo.spring.entity.Timesheet;
import com.demo.spring.entity.TimesheetDto;
import com.demo.spring.repo.TimesheetRepository;

import io.micrometer.core.annotation.Timed;

@RestController
@RequestMapping("e")
@Timed(value = "empTimesheet_app")
public class TimesheetRestController {
	
	@Autowired
	TimesheetRepository trepo;
	
	static Logger logger=LoggerFactory.getLogger(TimesheetRestController.class);
	
	
	//-------------------------------- add timesheet to the employee -----------------------------------
	
	@PostMapping(path = "/timesheet/add",consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> addTimesheet(@RequestBody TimesheetDto t) {
		logger.info("Adding timesheet for employee");
		if (trepo.existsById(t.getTno())) {
			logger.error("Timesheet can not be created for the employee");
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
			logger.info("Suuccessfully added timesheet for the employee");
			return ResponseEntity.ok("{\"status\":\"Timesheet Added to the Employee\"}");
		}
	}
	
	
	
	//---------------------------------- view timesheet record ---------------------------------
	
	@GetMapping(path = "/timesheet/list/{empno}",produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Timesheet>> getTimesheetList(@PathVariable("empno") int empNo) {
		logger.info("Getting data of timesheets applied by the employee with id "+empNo);
		return ResponseEntity.ok(trepo.findAllByEmpNo(empNo));
	}
	
}
