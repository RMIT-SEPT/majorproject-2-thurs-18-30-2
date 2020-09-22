package com.majorproject.backend;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.services.CustomerService;
import com.majorproject.backend.services.EmployeeService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.EmployeeController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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
@WebMvcTest(EmployeeService.class)
public class EmployeeBServiceTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService employeeService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;

    Employee employee;

    @Before
    public void createCustomer(){
        employee =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");
    }

    @Test
    public void customerCreate_Pass() throws Exception {
        given(employeeService.saveOrUpdateEmployee(employee)).willReturn(employee);

        Employee actualEmployee = employeeService.saveOrUpdateEmployee(employee);
        Assert.assertEquals(actualEmployee, employee);
    }

    @Test
    public void employeeCreate_Fail() throws Exception {
        Employee employee2 =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");

        given(employeeService.saveOrUpdateEmployee(employee)).willReturn(employee);
        given(employeeService.saveOrUpdateEmployee(employee2)).willReturn(employee2);

        Assert.assertNotEquals(employee2, employee);
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