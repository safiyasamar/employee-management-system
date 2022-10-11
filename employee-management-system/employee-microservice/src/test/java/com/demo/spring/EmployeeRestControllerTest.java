package com.demo.spring;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Employee;
import com.demo.spring.repo.EmployeeRepository;
import com.demo.spring.rest.EmployeeRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeRestControllerTest {

	@Autowired
	MockMvc mvc;

	@MockBean
	EmployeeRepository erepo;

	@InjectMocks
	EmployeeRestController erc;

	//Test for edit employee details
	@Test
	void testEditEmpDetails() throws Exception {
		Employee e = new Employee(102, "Arun", "Ranchi", 45000);
		when(erepo.existsById(102)).thenReturn(true);
		when(erepo.save(e)).thenReturn(e);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(put("/e/employee").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Employee Updated Successfully"));

	}
	
	
	//Negative Test for edit employee details

	@Test
	void testEditEmpDetailsNegative() throws Exception {
		Employee e = new Employee(109, "Puja", "Noida", 45000);
		when(erepo.existsById(109)).thenReturn(false);

		ObjectMapper om = new ObjectMapper();
		String value = om.writeValueAsString(e);

		mvc.perform(put("/e/employee").contentType(MediaType.APPLICATION_JSON).content(value))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Employee doesn't exist"));
	}

}
