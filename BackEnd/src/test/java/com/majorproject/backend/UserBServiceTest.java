package com.majorproject.backend;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.User;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.services.UserService;
import com.majorproject.backend.util.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
public class UserBServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private EmployeeRepository employeeRepository;

    @MockBean
    private CustomerRepository customerRepository;


    @Test
    public void login_Pass() throws Exception {
        Employee employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "admin");

        given(employeeRepository.findByUsernameAndPassword("usernameABC", "pw1234")).willReturn(employeeJohn);
        LoginForm requestBody = new LoginForm();
        requestBody.setUsername("usernameABC");
        requestBody.setPassword("pw1234");

        User user = userService.loginUser(requestBody);

        Assert.assertEquals(user.getEmail(), employeeJohn.getEmail());
    }

    @Test
    public void login_Fail() throws Exception {
        given(employeeRepository.findByUsernameAndPassword("usernameABC", "pw1234")).willReturn(null);
        given(customerRepository.findByUsernameAndPassword("usernameABC", "pw1234")).willReturn(null);
        LoginForm requestBody = new LoginForm();
        requestBody.setUsername("usernameABC");
        requestBody.setPassword("pw1234");
        try {
            User user = userService.loginUser(requestBody);
        } catch(Exception e) {
            Assert.assertEquals(e.getMessage(), "Username or password is invalid");
        }

    }

//    @Test void usernameExists
}
