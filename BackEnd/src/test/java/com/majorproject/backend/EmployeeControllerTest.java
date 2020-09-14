package com.majorproject.backend;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.services.EmployeeService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.EmployeeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.validation.BindingResult;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
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
    private BindingResult result;

    @Test
    public void employeeCreate_Pass() throws Exception {
        Employee employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");

        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(employeeJohn))).andExpect(status().isOk());
    }

//    @Test
//    public void employeeGetByUsername_Pass() throws Exception {
//        List<Employee> allEmployees = Arrays.asList(employeeJohn);
//
//        String username = employeeJohn.getUsername();
//
//        given(employeeService.getEmployeeByUsername(username)).willReturn(employeeJohn);
//
//        MvcResult result = mvc.perform(get("/api/employee/test/getEmployeeByUsername")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(username)))
//                .andReturn();
//
//        String text = result.getRequest().getContentAsString();
//        System.out.println("text: " + text);
//    }

//    @Test
//    public void employeeLogin_Pass() throws Exception {
//        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employee);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setUsername("usernameABC");
//        requestBody.setPassword("pw1234");
//
//        mvc.perform(post("/api/user/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody)))
//                .andExpect(status().is(200));
//                .andExpect(content().json("{'fName' : 'John'}"));
//    }
//
//    @Test
//    public void employeeLogin_pwdWrong_Fail() throws Exception {
//        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employee);
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
//        given(employeeService.getEmployeeByUsername("usernameABC")).willReturn(employee);
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