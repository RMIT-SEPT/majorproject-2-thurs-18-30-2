package com.majorproject.backend;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.services.CustomerService;
import com.majorproject.backend.web.CustomerController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CustomerService customerService;
    private Customer customer =  new Customer("Ross", "Bob", "bob@gmail.com",
                                                "usernameABC", "pw1234",
                                                "address", "0123456");

    @Test
    public void customerLogin_Pass() throws Exception {
//        given(customerService.getCustomerByEmail("bob@gmail.com")).willReturn(customer);
//
//        LoginForm requestBody = new LoginForm();
////        requestBody.setEmail("bob@gmail.com");
//        requestBody.setUsername("usernameABC");
//        requestBody.setPassword("pw1234");
//
////        mvc.perform(post("/api/customer/verify")
//        mvc.perform(post("/api/user/login")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(Util.asJsonString(requestBody))
//                    )
//                .andExpect(status().isOk())
//                .andExpect(content().json("{'fName' : 'Ross'}"));
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
