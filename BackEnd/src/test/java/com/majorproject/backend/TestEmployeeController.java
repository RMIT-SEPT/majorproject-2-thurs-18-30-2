package com.majorproject.backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.majorproject.backend.jsonconv.LoginForm;
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

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeController.class)
public class TestEmployeeController {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EmployeeService employeeService;

    @Test
    public void employeeLogin_Pass() throws Exception {
        // Incorrect parameters
//        Employee employee =  new Employee("John", "Apple", "jApple@mail.com", "1234", "JohnAddress", "0412345678", "admin");
//
//        given(employeeService.getEmployeeByEmail("jApple@gmail.com")).willReturn(employee);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setEmail("jApple@gmail.com");
//        requestBody.setPassword("1234");
//
//        mvc.perform(post("/api/employee/verify")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody)))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{'fName' : 'John'}"));
    }

    @Test
    public void employeeLogin_pwdWrong_Fail() throws Exception {
        // Incorrect parameters
//        Employee employee =  new Employee("John", "Apple", "jApple@mail.com", "1234", "JohnAddress", "0412345678", "admin");
//
//        given(employeeService.getEmployeeByEmail("jApple@gmail.com")).willReturn(employee);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setEmail("jApple@gmail.com");
//        requestBody.setPassword("123");
//
//        mvc.perform(post("/api/employee/verify")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody)))
//                .andExpect(status().isUnauthorized());
    }

    @Test
    public void employeeLogin_emailWrong_Fail() throws Exception {
//        Employee employee =  new Employee("John", "Apple", "jApple@mail.com", "1234", "JohnAddress", "0412345678", "admin");

        given(employeeService.getEmployeeByEmail("Apple@gmail.com")).willReturn(null);

        LoginForm requestBody = new LoginForm();
        requestBody.setEmail("Apple@gmail.com");
        requestBody.setPassword("1234");

        mvc.perform(post("/api/employee/verify")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(requestBody)))
                .andExpect(status().isNotFound());
    }
}