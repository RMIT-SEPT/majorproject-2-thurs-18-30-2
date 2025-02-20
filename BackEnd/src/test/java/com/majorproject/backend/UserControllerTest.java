package com.majorproject.backend;

import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.UserService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.UserController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    CustomUserDetailsService customUserDetailsService;


    private Employee employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
                                                    "usernameABC", "pw1234", "JohnAddress",
                                                    "0412345678", "admin");

    @Test
    public void login_Pass() throws Exception {
        List<Employee> allEmployees = Arrays.asList(employeeJohn);

        LoginForm requestBody = new LoginForm();
        requestBody.setUsername("usernameABC");
        requestBody.setPassword("pw1234");

        mvc.perform(post("/api/user/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(Util.asJsonString(requestBody)))
                .andExpect(status().isOk());
    }
}
