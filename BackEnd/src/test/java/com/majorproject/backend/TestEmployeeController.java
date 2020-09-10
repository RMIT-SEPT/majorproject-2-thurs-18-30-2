package com.majorproject.backend;

//<<<<<<< Updated upstream
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.majorproject.backend.jsonconv.LoginForm;
//import com.majorproject.backend.models.Employee;
//import com.majorproject.backend.models.User;
//=======
//>>>>>>> Stashed changes
//import com.majorproject.backend.services.EmployeeService;
//import com.majorproject.backend.web.EmployeeController;
//import com.majorproject.backend.web.UserController;
//import org.junit.Test;
//import org.junit.runner.RunWith;import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//<<<<<<< Updated upstream
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultMatcher;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static net.bytebuddy.matcher.ElementMatchers.is;
//import static org.hamcrest.Matchers.*;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.junit.Assert.*;
//
//=======
//
//import static net.bytebuddy.matcher.ElementMatchers.is;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//>>>>>>> Stashed changes
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(EmployeeController.class)
public class TestEmployeeController {
//    @Autowired
//    private MockMvc mvc;
//    @MockBean
//    private EmployeeService employeeService;
//    private Employee employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
//                                                "usernameABC", "pw1234", "JohnAddress",
//                                                "0412345678", "admin");
//
//    @Test
//    public void employeeCreate_Pass() throws Exception {
//        List<Employee> allEmployees = Arrays.asList(employeeJohn);
//
//        given(employeeService.getAllEmployees()).willReturn(allEmployees);
//
//        mvc.perform(get("/api/employee/test/getAllEmployees")
//                .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$", hasSize(1)));
//    }
//
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