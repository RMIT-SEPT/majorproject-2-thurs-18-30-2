package com.majorproject.backend;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.responseForms.LoginForm;
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
    //    private Employee employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
//                                                "usernameABC", "pw1234", "JohnAddress",
//                                                "0412345678", "employee");
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    @MockBean
    private EmployeeController employeeController;
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
        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn))).andExpect(status().isCreated());
    }

    @Test
    public void employeeCreate_Fail() throws Exception {
        employeeJohn = null;

        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void employeeEdit_Pass() throws Exception {
        List<Employee> allEmployees = Arrays.asList(employeeJohn);
        //.isCreated(): 201 status (creating a new request)
        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn)))
                .andExpect(status().is2xxSuccessful());

        Employee employeeJohnEdit =  new Employee("John", "Carrot", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");

        //given(employeeController.editEmployee(employeeJohn.getUsername(), employeeJohn, result));

        mvc.perform(post("/api/employee/usernameABC/editEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohnEdit)))
                .andExpect(status().isOk());
    }

    @Test
    public void employeeEdit_Fail() throws Exception {
        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn))).andExpect(status().isCreated());

        employeeJohn = null;

        mvc.perform(post("/api/employee/usernameABC/editEmployee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn)))
                .andExpect(status().is4xxClientError());
    }
//
//    @Test
//    public void employeeGetByUsername_Pass() throws Exception {
//        Employee employee =  new Employee("John", "Apple", "jApple@mail.com",
//                "usernameABC", "pw1234", "JohnAddress",
//                "0412345678", "employee");
//        List<Employee> allEmployees = Arrays.asList(employee);
//
//        String username = employee.getUsername();
//
//        given(employeeService.getEmployeeByUsername(username)).willReturn(employee);
//
//        MvcResult result = mvc.perform(get("/api/test/getEmployeeByUsername")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(username)))
//                .andReturn();
//
//    }
//
//    @Test
//    public void employeeLogin_Pass() throws Exception {
//        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employeeJohn);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setUsername("usernameABC");
//        requestBody.setPassword("pw1234");
//
//        mvc.perform(post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody)))
//                .andExpect(status().is(200))
//                .andExpect(content().json("{'fName' : 'John'}"));
//    }
//
//    @Test
//    public void employeeLogin_pwdWrong_Fail() throws Exception {
//        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employeeJohn);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setUsername("usernameABC");
//        requestBody.setPassword("WRONG");
//
//        mvc.perform(post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody)))
//                .andExpect(status().isUnauthorized());
//    }
//
//    @Test
//    public void employeeLogin_emailWrong_Fail() throws Exception {
//        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employeeJohn);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setUsername("NOPE");
//        requestBody.setPassword("pw1234");
//
//        mvc.perform(post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody)))
//                .andExpect(status().isUnauthorized());
//    }
}