package com.majorproject.backend;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.User;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.UserService;
import com.majorproject.backend.util.Util;
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
import org.springframework.validation.BindingResult;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserService.class)
public class UserServiceTest {
    @Autowired
    private UserService userService;
    @MockBean
    private EmployeeRepository employeeRepository;
    @MockBean
    private CustomerRepository customerRepository;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    CustomUserDetailsService customUserDetailsService;

    private Employee employeeJohn;
    private Employee employeeDan;
    private Customer customerAnt;

    @Before
    public void runBefore() {
        employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "admin");

        employeeDan =  new Employee("Dan", "Nad", "dN@mail.com",
                "usernameDN", "pwDN123", "DanAddress",
                "0448382837", "employee");

        customerAnt = new Customer("Ant", "Hony", "aH@mail.com", "usernameAnt",
                "pwAnt123", "AntAddress,", "0293817232");
    }

    @Test
    public void login_Pass() throws Exception {
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

    @Test
    public void usernameExistsEmployee_Pass() throws Exception {
        String username = employeeJohn.getUsername();

        given(employeeRepository.findByUsername(username)).willReturn(employeeJohn);
        given(customerRepository.findByUsername(username)).willReturn(null);

        String result = userService.checkIfUsernameExists(username);
        Assert.assertEquals(result, "true");
    }

    @Test
    public void usernameExistsCustomer_Pass() throws Exception {
        String username = customerAnt.getUsername();

        given(employeeRepository.findByUsername(username)).willReturn(null);
        given(customerRepository.findByUsername(username)).willReturn(customerAnt);

        String result = userService.checkIfUsernameExists(username);
        Assert.assertEquals(result, "true");
    }

    @Test
    public void usernameExists_Fail() throws Exception {
        String username = "ree";

        given(employeeRepository.findByUsername(username)).willReturn(null);
        given(customerRepository.findByUsername(username)).willReturn(null);

        String result = userService.checkIfUsernameExists(username);
        Assert.assertEquals(result, "false");
    }

    /* Assume employeeJohn id = 1, employeeDan id = 2 and customerAnt id = 3 */

    @Test
    public void userTypeAdmin_Success() throws Exception {
        long id = 1;

        given(employeeRepository.findByEmployeeId(id)).willReturn(employeeJohn);
        given(customerRepository.findByCustomerId(id)).willReturn(null);

        String result = userService.getUserType(id);
        Assert.assertEquals(result, "admin"); // employeeJohn
    }

    @Test
    public void userTypeEmployee_Success() throws Exception {
        long id = 2;

        given(employeeRepository.findByEmployeeId(id)).willReturn(employeeDan);
        given(customerRepository.findByCustomerId(id)).willReturn(null);

        String result = userService.getUserType(id);
        Assert.assertEquals(result, "employee"); // employeeDan
    }

    @Test
    public void userTypeCustomer_Success() throws Exception {
        long id = 3;

        given(employeeRepository.findByEmployeeId(id)).willReturn(null);
        given(customerRepository.findByCustomerId(id)).willReturn(customerAnt);

        String result = userService.getUserType(id);
        Assert.assertEquals(result, "customer"); // customerAnt
    }

    @Test
    public void usernameGetEmployee_Success() {
        String username = employeeJohn.getUsername();

        given(employeeRepository.findByUsername(username)).willReturn(employeeJohn);
        given(customerRepository.findByUsername(username)).willReturn(null);

        User user = userService.getUserByUserName(username);
        Assert.assertEquals(user, employeeJohn);
    }

    @Test
    public void usernameGetCustomer_Success() {
        String username = customerAnt.getUsername();

        given(employeeRepository.findByUsername(username)).willReturn(null);
        given(customerRepository.findByUsername(username)).willReturn(customerAnt);

        User user = userService.getUserByUserName(username);
        Assert.assertEquals(user, customerAnt);
    }

    @Test
    public void usernameGetUser_Fail() {
        String username = "ree";

        given(employeeRepository.findByUsername(username)).willReturn(null);
        given(customerRepository.findByUsername(username)).willReturn(null);

        User user = userService.getUserByUserName(username);
        Assert.assertEquals(user, null);
    }
}
