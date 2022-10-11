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
import java.util.Optional;

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

import com.demo.spring.entity.Employee;
import com.demo.spring.repo.EmployeeRepository;
import com.demo.spring.rest.AdminEmpRestController;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdminEmpRestControllerTest {
	
	@Autowired
	MockMvc mvc;
	
	@MockBean
	EmployeeRepository erepo;
	
	@InjectMocks
	AdminEmpRestController erc;
	
	
	//Test for add new Employee
	@Test
	void testSave() throws Exception{
		Employee e=new Employee(120, "Rajiv", "Noida", 45000);
		when(erepo.existsById(120)).thenReturn(false);
		when(erepo.save(e)).thenReturn(e);
		
		ObjectMapper om=new ObjectMapper();
		String empJson=om.writeValueAsString(e);
		
		mvc.perform(post("/a/employee/add").contentType(MediaType.APPLICATION_JSON).
				content(empJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Employee Added Successfully"));
	}
	
	
	
	//Negative Test for add new Employee
	
	@Test
	void testSaveNegative() throws Exception{
		Employee e=new Employee(102, "Rajiv", "Noida", 45000);
		when(erepo.existsById(102)).thenReturn(true);
		
		ObjectMapper om=new ObjectMapper();
		String empJson=om.writeValueAsString(e);
		
		mvc.perform(post("/a/employee/add").contentType(MediaType.APPLICATION_JSON).
				content(empJson))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.status").value("Employee Already Exists"));
	}
	
	
	
	//Test for View Profile of the Employee
	@Test
	void testFindById() throws Exception {
		int id=104;
		Mockito.when(erepo.findById(104)).thenReturn(Optional.of(new Employee(104, "Ajay", "Hyderabad", 65000)));

		mvc.perform(get("/a/employee/104").accept(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(jsonPath("$.name").value("Ajay"));

	}
	
	
	
	@Test
	void testFindByIdNegative() throws Exception {

		mvc.perform(get("/a/employee/134")).andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.status").value("Employee does not exist"));

	}
	
	
	//Test Listing of all Employees
	@Test
    void testFindAllEmp() throws Exception{

        List<Employee> emp = new ArrayList<>();
        emp.add(new Employee(120,"Rajiv","Noida",90000));
        Mockito.when(erepo.findAll()).thenReturn(emp);

        mvc.perform(get("/a/employee/list").accept(MediaType.APPLICATION_JSON_VALUE))
            .andDo(print())
            .andExpectAll(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print());
    }

}
