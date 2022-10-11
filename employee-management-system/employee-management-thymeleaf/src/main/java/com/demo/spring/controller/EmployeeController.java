package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.entity.Employee;
import com.demo.spring.entity.Leave;
import com.demo.spring.entity.Timesheet;

@Controller
@RequestMapping("/u")
public class EmployeeController {

	@Autowired
	RestTemplate rt;

	//---------------------- for edit details of emaployee -------------------------

	@GetMapping(path = "editemp")
	public String getEmpPage(ModelMap map) {
		Employee employee = new Employee();
		map.addAttribute("employee", employee);
		return "edit-emp";
	}

	@PostMapping(path = "updateEmp")
	public ModelAndView saveEmp(@ModelAttribute("employee") Employee e) {
		ModelAndView mv = new ModelAndView();
		HttpEntity<Employee> req = new HttpEntity<>(e);
		ResponseEntity<String> resp = rt.exchange("http://emp-gateway-service/e/employee", HttpMethod.PUT, req,
				String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	

	//------------------------- for adding leave for employee ------------------------

	@GetMapping(path = "addLeave")
	public String getAddLeave(ModelMap map) {
		Leave leave = new Leave();
		map.addAttribute("leave", leave);
		return "add-leave";
	}

	@PostMapping(path = "saveLeave")
	public ModelAndView saveLeave(@ModelAttribute("leave") Leave l) {
		ModelAndView mv = new ModelAndView();
		HttpEntity<Leave> req = new HttpEntity<>(l);
		ResponseEntity<String> resp = rt.exchange("http://emp-gateway-service/e/leave/apply", HttpMethod.POST, req,
				String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	

	//-------------------------- for adding timesheet for the employee ----------------------

	@GetMapping(path = "addTimesheet")
	public String getAddTimesheet(ModelMap map) {
		Timesheet timesheet = new Timesheet();
		map.addAttribute("timesheet", timesheet);
		return "add-timesheet";
	}

	@PostMapping(path = "saveTimesheet")
	public ModelAndView saveTimesheet(@ModelAttribute("timesheet") Timesheet t) {
		ModelAndView mv = new ModelAndView();
		HttpEntity<Timesheet> req = new HttpEntity<>(t);
		ResponseEntity<String> resp = rt.exchange("http://emp-gateway-service/e/timesheet/add", HttpMethod.POST, req,
				String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	

	//------------------------------ for adding new employee -------------------------------

	@GetMapping(path = "addEmp")
	public String getAddEmp(ModelMap map) {
		Employee emp = new Employee();
		map.addAttribute("employee", emp);
		return "add-emp";
	}

	@PostMapping(path = "saveEmp")
	public ModelAndView saveemp(@ModelAttribute("employee") Employee e) {
		ModelAndView mv = new ModelAndView();
		HttpEntity<Employee> req = new HttpEntity<>(e);
		ResponseEntity<String> resp = rt.exchange("http://emp-gateway-service/a/employee/add", HttpMethod.POST, req,
				String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	

	//------------------------------ for finding an employee by id --------------------------

	@GetMapping(path = "find")
	public String getPage() {
		return "find-emp";
	}

	@PostMapping(path = "findemp")
	public ModelAndView getEmpDetails(@RequestParam("id") int id) {
		Employee emp = rt.getForObject("http://emp-gateway-service/a/employee/" + id, Employee.class);
		ModelAndView mv = new ModelAndView();
		mv.addObject("empData", emp);
		mv.setViewName("find-emp");
		return mv;
	}
	
	

	//------------------------------ for listing of all employee ------------------------------

	@GetMapping(path = "emplist")
	public ModelAndView listAllEmp() {
		ResponseEntity<List<Employee>> empList = rt.exchange("http://emp-gateway-service/a/employee/list", HttpMethod.GET,
				null, new ParameterizedTypeReference<List<Employee>>() {
				});
		ModelAndView mv = new ModelAndView();
		mv.addObject("emplist", empList.getBody());
		mv.setViewName("emplist");
		return mv;
	}

	//----------------------------- for listing of leaves for all employee --------------------------

	@GetMapping(path = "leavelist")
	public ModelAndView listAllLeave() {
		ResponseEntity<List<Leave>> leaveList = rt.exchange("http://emp-gateway-service/a/leave/list", HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Leave>>() {
				});
		ModelAndView mv = new ModelAndView();
		mv.addObject("leavelist", leaveList.getBody());
		mv.setViewName("leavelist");
		return mv;
	}
	
	

	//------------------------------ for approve/disapprove of leave -----------------------------------

	@GetMapping(path = "editleave")
	public String getLeavePage(ModelMap map) {
		Leave leave = new Leave();
		map.addAttribute("leave", leave);
		return "edit-leave";
	}

	@PostMapping(path = "updateLeave")
	public ModelAndView updateLeave(@ModelAttribute("leave") Leave l) {
		ModelAndView mv = new ModelAndView();
		HttpEntity<Leave> req = new HttpEntity<>(l);
		ResponseEntity<String> resp = rt.exchange("http://emp-gateway-service/a/leave/status", HttpMethod.PUT, req,
				String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	

	//-------------------------------- for editing timesheet ---------------------------------

	@GetMapping(path = "edittimesheet")
	public String getTimesheetPage(ModelMap map) {
		Timesheet timesheet = new Timesheet();
		map.addAttribute("timesheet", timesheet);
		return "edit-timesheet";
	}

	@PostMapping(path = "updateTimesheet")
	public ModelAndView updateLeave(@ModelAttribute("timesheet") Timesheet t) {
		ModelAndView mv = new ModelAndView();
		HttpEntity<Timesheet> req = new HttpEntity<>(t);
		ResponseEntity<String> resp = rt.exchange("http://emp-gateway-service/a/timesheet/edit", HttpMethod.PUT, req,
				String.class);
		mv.setViewName("save-response");
		mv.addObject("resp", resp.getBody());
		return mv;
	}
	
	

	//-------------------------------- for listing of timesheet -------------------------------

	@GetMapping(path = "timesheetlist")
	public ModelAndView listAllTimesheet() {
		ResponseEntity<List<Timesheet>> leaveList = rt.exchange("http://emp-gateway-service/a/timesheet/list",
				HttpMethod.GET, null, new ParameterizedTypeReference<List<Timesheet>>() {
				});
		ModelAndView mv = new ModelAndView();
		mv.addObject("timesheetlist", leaveList.getBody());
		mv.setViewName("timesheetlist");
		return mv;
	}

}
