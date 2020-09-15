package com.majorproject.backend;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.services.CustomerService;
import com.majorproject.backend.services.MapValidationErrorService;
import com.majorproject.backend.util.Util;
import com.majorproject.backend.web.CustomerController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.BDDMockito.given;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    private Customer customer;

    @Before
    public void customerObject(){
        customer =  new Customer("Ross", "Bob", "bob@gmail.com",
                "usernameABC", "pw1234",
                "address", "0123456");
    }

    @Test
    public void customerCreate_Pass() throws Exception {
        //.isCreated(): 201 status (creating a new request)
        mvc.perform(post("/api/customer/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(customer))).andExpect(status().isCreated());
    }

    @Test
    public void customerCreate_Fail() throws Exception {
        customer = null;

        mvc.perform(post("/api/employee/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Util.asJsonString(customer)))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void customerLogin_pwdWrong_Fail() throws Exception {
        // Incorrect parameters
//        Customer customer =  new Customer("Ross", "Bob", "bob@gmail.com", "1234", null, null);
//
//        given(customerService.getCustomerByEmail("bob@gmail.com")).willReturn(customer);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setEmail("bob@gmail.com");
//        requestBody.setPassword("123");
//
//        mvc.perform(post("/api/customer/verify")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody))
//        ).andExpect(status().isUnauthorized());
    }

    @Test
    public void customerLogin_emailWrong_Fail() throws Exception {
//        given(customerService.getCustomerByEmail("ob@gmail.com")).willReturn(null);
//
//        LoginForm requestBody = new LoginForm();
//        requestBody.setEmail("ob@gmail.com");
//        requestBody.setPassword("1234");
//
//        mvc.perform(post("/api/customer/verify")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(Util.asJsonString(requestBody))
//        ).andExpect(status().isNotFound());
    }
}
