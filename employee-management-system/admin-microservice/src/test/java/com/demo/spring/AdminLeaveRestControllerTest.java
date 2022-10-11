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

import com.demo.spring.entity.Leave;
import com.demo.spring.repo.LeaveRepository;
import com.demo.spring.rest.AdminLeaveRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminLeaveRestControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	LeaveRepository lrepo;
	
	@InjectMocks
	AdminLeaveRestController erc;
	
	
	//Test for Add Leave for the Employee
	
	@Test
	void testSave() throws Exception{
		Leave l=new Leave(103, 9, 2, "Casual", "Applied");
		when(lrepo.existsById(9)).thenReturn(false);
		when(lrepo.save(l)).thenReturn(l);
		
		ObjectMapper om=new ObjectMapper();
		String leaveJson=om.writeValueAsString(l);
		
		mvc.perform(post("/a/leave/apply").contentType(MediaType.APPLICATION_JSON).
				content(leaveJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Leave Applied By Employee"));
	}
	
	
	
	//Negative Test for Add Leave for the Employee
	
	@Test
	void testSaveNegative() throws Exception{
		Leave l=new Leave(103, 4, 2, "Casual", "Applied");
		when(lrepo.existsById(4)).thenReturn(true);
		
		ObjectMapper om=new ObjectMapper();
		String leaveJson=om.writeValueAsString(l);
		
		mvc.perform(post("/a/leave/apply").contentType(MediaType.APPLICATION_JSON).
				content(leaveJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Something went wrong"));
	}
	
	
	
	
	
	//Test for Listing of the Leave for all Employees
	@Test
    void testFindAllLeave() throws Exception{

        List<Leave> leaves = new ArrayList<>();
        leaves.add(new Leave(101, 4, 2, "Sick-Leave", "Applied"));
        Mockito.when(lrepo.findAll()).thenReturn(leaves);

        mvc.perform(get("/a/leave/list").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpectAll(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
    }
	
	
	//Test for Approve/Disapprove Leave
	@Test
    void testEditLeave() throws Exception{
        Leave l=new Leave(102, 1, 3, "Casual", "Approved");
        when(lrepo.existsById(1)).thenReturn(true);
        when(lrepo.save(l)).thenReturn(l);

        ObjectMapper om=new ObjectMapper();
        String value=om.writeValueAsString(l);

        mvc.perform(put("/a/leave/status").contentType(MediaType.APPLICATION_JSON).content(value))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("Status Changed Successfully"));

    }

	
	
	//Neagtive Test for Approve/Disapprove Leave
	
	@Test
    void testEditLeaveNegative() throws Exception{
        Leave l=new Leave(102, 8, 3, "Casual", "Approved");
        when(lrepo.existsById(8)).thenReturn(false);
        when(lrepo.save(l)).thenReturn(l);

        ObjectMapper om=new ObjectMapper();
        String value=om.writeValueAsString(l);

        mvc.perform(put("/a/leave/status").contentType(MediaType.APPLICATION_JSON).content(value))
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.status").value("No such leave exists"));

    }

	
	

}
