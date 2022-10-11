package com.demo.spring;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.demo.spring.entity.Payslip;
import com.demo.spring.repo.PayslipRepository;
import com.demo.spring.rest.EmployeeRestController;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class PayslipRestControllerTest {
	@Autowired
	MockMvc mvc;
	
	@MockBean
	PayslipRepository prepo;
	
	@InjectMocks
	EmployeeRestController erc;
	
	//Test for view payslip
	@Test
    void testFindPayslip() throws Exception{

        List<Payslip> payslip = new ArrayList<>();
        payslip.add(new Payslip(100, 1, "May", 52000));
        Mockito.when(prepo.findAllByEmpNo(100)).thenReturn(payslip);

        mvc.perform(get("/e/payslip/list/100").accept(MediaType.APPLICATION_JSON_VALUE))
            .andExpectAll(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(jsonPath("$.[0].empNo").value("100"));
    }

}
