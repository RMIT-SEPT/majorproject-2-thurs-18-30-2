package com.majorproject.backend;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.CustomerService;
import com.majorproject.backend.services.MapValidationErrorService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerService.class)
public class CustomerServiceTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private CustomerService customerService;
    @MockBean
    private MapValidationErrorService mapValidationErrorService;
    @MockBean
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @MockBean
    private JwtTokenProvider tokenProvider;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    CustomUserDetailsService customUserDetailsService;

    Customer customerJohn;

    @Before
    public void createCustomer(){
        customerJohn =  new Customer("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678");
    }

    @Test
    public void customerCreate_Pass() throws Exception {
        given(customerService.saveOrUpdateCustomer(customerJohn)).willReturn(customerJohn);

        Customer actualCustomer = customerService.saveOrUpdateCustomer(customerJohn);
        Assert.assertEquals(actualCustomer, customerJohn);

    }

    @Test
    public void customerCreate_Fail() throws Exception {
        Customer customer2 =  new Customer("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678");

        given(customerService.saveOrUpdateCustomer(customerJohn)).willReturn(customerJohn);
        given(customerService.saveOrUpdateCustomer(customer2)).willReturn(customer2);

        Assert.assertNotEquals(customer2, customerJohn);
    }

    @Test
    public void employeeUsername_Success() throws Exception {
        String username = customerJohn.getUsername();

        given(customerService.getCustomerByUsername(username)).willReturn(customerJohn);
        Customer customer = customerService.getCustomerByUsername(username);

        Assert.assertEquals(customer, customerJohn);
    }

    @Test
    public void employeeUsername_Fail() throws Exception {
        String username = "ree";

        given(customerService.getCustomerByUsername(username)).willReturn(null);
        Customer customer = customerService.getCustomerByUsername(username);

        Assert.assertNotEquals(customer, customerJohn);
    }

    @Test
    public void editCustomer() throws Exception {
        Customer editedCustomerJohn = new Customer("No", "No", "NN@gmail.com", "usernameN",
                "pwN", "NAddress", "0192938498");
        String customerJohnId = "1";

        given(customerService.editCustomer(customerJohnId, editedCustomerJohn)).willReturn(customerJohn);

        Customer editedCustomer = customerService.editCustomer(customerJohnId, editedCustomerJohn);

        Assert.assertEquals(editedCustomer.getId(), customerJohn.getId());
        Assert.assertEquals(editedCustomer.getEmail(), customerJohn.getEmail());
    }
}