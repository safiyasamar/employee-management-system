package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Timesheet;
import com.demo.spring.repo.TimesheetRepository;
import com.demo.spring.rest.EmployeeRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class TimesheetRestControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	TimesheetRepository trepo;
	
	@InjectMocks
	EmployeeRestController erc;
	
	
	//Test for add timesheet to the employee
	@Test
	void testSave() throws Exception{
		Timesheet t=new Timesheet(132, 1010, 20002, "Tech-Project", "Noida", 40);
		when(trepo.existsById(1010)).thenReturn(false);
		Mockito.when(trepo.save(t)).thenReturn(t);
		
		ObjectMapper om=new ObjectMapper();
		String timeJson=om.writeValueAsString(t);
		
		mvc.perform(post("/e/timesheet/add")
				.contentType(MediaType.APPLICATION_JSON).
				content(timeJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Timesheet Added to the Employee"));
	}
	
	
	
	//Negative Test for add timesheet to the employee
	
	@Test
	void testSaveNegative() throws Exception{
		Timesheet t=new Timesheet(106, 1005, 20002, "Tech-Project", "Noida", 40);
		when(trepo.existsById(1005)).thenReturn(true);
		
		ObjectMapper om=new ObjectMapper();
		String timeJson=om.writeValueAsString(t);
		
		mvc.perform(post("/e/timesheet/add")
				.contentType(MediaType.APPLICATION_JSON).
				content(timeJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Can't create this timesheet"));
	}
	
	
	//Test view timesheet record
	@Test
    void testFindTimesheets() throws Exception{

        List<Timesheet> timesheet= new ArrayList<>();
        timesheet.add(new Timesheet(100, 5, 20001, "Training", "Blore", 40));
        Mockito.when(trepo.findAllByEmpNo(100)).thenReturn(timesheet);

        mvc.perform(get("/e/timesheet/list/100").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpectAll(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.[0].empNo").value("100"));
    }
}
