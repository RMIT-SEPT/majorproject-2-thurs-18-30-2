package com.majorproject.backend;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.EmployeeService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.EmployeeController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;


import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    @MockBean
    private EmployeeController employeeController;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    CustomUserDetailsService customUserDetailsService;
    private BindingResult result;
    private Employee employeeJohn;

    @Before
    public void employeeObject(){
        employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");
    }

    @Test
    public void employeeCreate_Pass() throws Exception {
        //.isCreated(): 201 status (creating a new request)
        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employeeJohn);
        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn))).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void employeeEdit_Pass() throws Exception {
        List<Employee> allEmployees = Arrays.asList(employeeJohn);
        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employeeJohn);
        //.isCreated(): 201 status (creating a new request)
        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn)))
                .andExpect(status().is2xxSuccessful());

        Employee employeeJohnEdit =  new Employee("John", "Carrot", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");

        mvc.perform(post("/api/employee/editEmployee/usernameABC")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohnEdit)))
                .andExpect(status().is2xxSuccessful());
    }
}