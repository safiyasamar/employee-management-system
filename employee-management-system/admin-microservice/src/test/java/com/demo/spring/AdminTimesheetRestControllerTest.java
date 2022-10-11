package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
import com.demo.spring.rest.AdminTimesheetRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminTimesheetRestControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	TimesheetRepository trepo;
	
	@InjectMocks
	AdminTimesheetRestController erc;
	
	
	//Test for Add Timesheet for the Employee
	@Test
	void testSave() throws Exception{
		Timesheet t=new Timesheet(102, 1009, 20002, "Tech-Project", "Noida", 40);
		when(trepo.existsById(1009)).thenReturn(false);
		when(trepo.save(t)).thenReturn(t);
		
		ObjectMapper om=new ObjectMapper();
		String timeJson=om.writeValueAsString(t);
		
		mvc.perform(post("/a/timesheet/add").contentType(MediaType.APPLICATION_JSON).
				content(timeJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Timesheet Added to the Employee"));
	}
	
	
	
	
	//Negative Test for Add Timesheet for the Employee
	
	@Test
	void testSaveNegative() throws Exception{
		Timesheet t=new Timesheet(102, 1002, 20002, "Tech-Project", "Noida", 40);
		when(trepo.existsById(1002)).thenReturn(true);
		
		ObjectMapper om=new ObjectMapper();
		String timeJson=om.writeValueAsString(t);
		
		mvc.perform(post("/a/timesheet/add").contentType(MediaType.APPLICATION_JSON).
				content(timeJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Can't create this timesheet"));
	}
	
	
	
	
	//Test for Edit Timesheet for the Employee
	@Test
    void testEditTimesheet() throws Exception{
        Timesheet t=new Timesheet(102, 1001, 20001, "Training", "Noida", 40);
        when(trepo.existsById(1001)).thenReturn(true);
        when(trepo.save(t)).thenReturn(t);

        ObjectMapper om=new ObjectMapper();
        String value=om.writeValueAsString(t);

        mvc.perform(put("/a/timesheet/edit").contentType(MediaType.APPLICATION_JSON).content(value))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("Timesheet edited for the employee"));

    }
	
	
	
	//Negative Test for Edit Timesheet for the Employee
	
	@Test
    void testEditTimesheetNegative() throws Exception{
        Timesheet t=new Timesheet(102, 1009, 20001, "Training", "Noida", 40);
        when(trepo.existsById(1009)).thenReturn(false);

        ObjectMapper om=new ObjectMapper();
        String value=om.writeValueAsString(t);

        mvc.perform(put("/a/timesheet/edit").contentType(MediaType.APPLICATION_JSON).content(value))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("Timesheet does not exist"));

    }
	
	
	
	
	
	//Test for Listing of the Timesheet
	@Test
    void testFindAllTimesheets() throws Exception{

        List<Timesheet> timesheet= new ArrayList<>();
        timesheet.add(new Timesheet(100, 5, 20001, "Training", "Blore", 40));
        Mockito.when(trepo.findAll()).thenReturn(timesheet);

        mvc.perform(get("/a/timesheet/list").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpectAll(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
    }
}
