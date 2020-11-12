package io.neca.springbootbackend.controller;

import static org.assertj.core.api.Assertions.*;

import io.neca.springbootbackend.model.Employee;
import io.neca.springbootbackend.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class EmployeeControllerTest {

    @Autowired
    private EmployeeController controller;

    @Autowired
    EmployeeService service;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Just simple testing with assertj")
    public void getEmployeeTest() throws Exception {
        this.mockMvc.perform(get("/api/v1/employees")).andDo(print()).andExpect(status().isOk());
    }

    @Test
    @DisplayName("assertj test version 2 i hope this will work")
    public void  getEmployeeTestV2() {
        Optional<Employee> employee = service.getOne(1);
        assertThat(employee).isNotNull();
    }

}