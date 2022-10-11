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

import com.demo.spring.entity.Leave;
import com.demo.spring.repo.LeaveRepository;
import com.demo.spring.rest.EmployeeRestController;
import com.fasterxml.jackson.databind.ObjectMapper;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class LeaveRestControllerTest {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	LeaveRepository lrepo;
	
	@InjectMocks
	EmployeeRestController erc;
	
	
	//Test for apply leave
	@Test
	void testSave() throws Exception{
		Leave l=new Leave(102, 8, 2, "Casual", "Applied");
		when(lrepo.existsById(8)).thenReturn(false);
		Mockito.when(lrepo.save(l)).thenReturn(l);
		
		ObjectMapper om=new ObjectMapper();
		String leaveJson=om.writeValueAsString(l);
		
		mvc.perform(post("/e/leave/apply").contentType(MediaType.APPLICATION_JSON).
				content(leaveJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Leave Applied By Employee"));
	}
	
	
	
	//Negative Test for apply leave
	
	@Test
	void testSaveNegative() throws Exception{
		Leave l=new Leave(102, 4, 2, "Casual", "Applied");
		when(lrepo.existsById(4)).thenReturn(true);
		
		ObjectMapper om=new ObjectMapper();
		String leaveJson=om.writeValueAsString(l);
		
		mvc.perform(post("/e/leave/apply").contentType(MediaType.APPLICATION_JSON).
				content(leaveJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Something went wrong"));
	}
	
	
	
	
	//Test for view leave records
	@Test
    void testFindAllLeave() throws Exception{

        List<Leave> leaves = new ArrayList<>();
        leaves.add(new Leave(101, 4, 2, "Sick-Leave", "Applied"));
        Mockito.when(lrepo.findAllByEmpNo(101)).thenReturn(leaves);

        mvc.perform(get("/e/leave/list/101").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpectAll(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.[0].empNo").value("101"));
    }
}
