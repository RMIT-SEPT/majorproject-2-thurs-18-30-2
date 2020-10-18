package com.majorproject.backend;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.security.JwtAuthenticationEntryPoint;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.EmployeeService;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@WebMvcTest(EmployeeService.class)
public class EmployeeServiceTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService employeeService;
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

    private Employee employeeJohn;

    @Before
    public void createCustomer(){
        employeeJohn =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "admin");
    }

    @Test
    public void customerCreate_Pass() throws Exception {
        given(employeeService.saveOrUpdateEmployee(employeeJohn)).willReturn(employeeJohn);

        Employee actualEmployee = employeeService.saveOrUpdateEmployee(employeeJohn);
        Assert.assertEquals(actualEmployee, employeeJohn);
    }

    @Test
    public void employeeCreate_Fail() throws Exception {
        Employee employee2 =  new Employee("John", "Apple", "jApple@mail.com",
                "usernameABC", "pw1234", "JohnAddress",
                "0412345678", "employee");

        given(employeeService.saveOrUpdateEmployee(employeeJohn)).willReturn(employeeJohn);
        given(employeeService.saveOrUpdateEmployee(employee2)).willReturn(employee2);

        Assert.assertNotEquals(employee2, employeeJohn);
    }

    @Test
    public void employeeUsername_Success() throws Exception {
        String username = employeeJohn.getUsername();

        given(employeeService.getEmployeeByUsername(username)).willReturn(employeeJohn);
        Employee employee = employeeService.getEmployeeByUsername(username);

        Assert.assertEquals(employee, employeeJohn);
    }

    @Test
    public void employeeUsername_Fail() throws Exception {
        String username = "ree";

        given(employeeService.getEmployeeByUsername(username)).willReturn(null);
        Employee employee = employeeService.getEmployeeByUsername(username);

        Assert.assertNotEquals(employee, employeeJohn);
    }

    @Test
    public void employeeId_Success() throws Exception {
        String id = "1"; // Assume its 1

        given(employeeService.getEmployeeById(id)).willReturn(employeeJohn);
        Employee employee = employeeService.getEmployeeById(id);

        Assert.assertEquals(employee, employeeJohn);
    }

    @Test
    public void employeeId_Fail() throws Exception {
        Employee employee2 = new Employee("AnAnt", "TTony", "aAT@mail.com",
                "usernameABC", "pw1234", "AntAddress",
                "0412345678", "employee");
        String employee2Id = "2"; // Assume its 2

        given(employeeService.getEmployeeById(employee2Id)).willReturn(employee2);
        Employee employee = employeeService.getEmployeeById(employee2Id);

        Assert.assertNotEquals(employee, employeeJohn);
    }

    @Test
    public void getEmployeeList() throws Exception {
        List<Employee> employeeList = new ArrayList<Employee>();
        employeeList.add(employeeJohn);

        given(employeeService.getAllEmployees()).willReturn(employeeList);

        Assert.assertEquals(employeeList.size(), 1);
    }

    @Test
    public void editEmployee() throws Exception {
        Employee editedEmployeeJohn = new Employee("No", "No", "NN@gmail.com", "usernameN",
                "pwN", "NAddress", "0192938498", "employee");
        String employeeJohnId = "1";

        given(employeeService.editEmployee(employeeJohnId, editedEmployeeJohn)).willReturn(employeeJohn);

        Employee editedEmployee = employeeService.editEmployee(employeeJohnId, editedEmployeeJohn);

        Assert.assertEquals(editedEmployee.getId(), employeeJohn.getId());
        Assert.assertEquals(editedEmployee.getEmail(), employeeJohn.getEmail());
    }
}