package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private CustomerService customerService;

    public User loginUser(LoginForm loginForm) {
        User user = null;
        boolean is_verified = false;
//        String email = loginForm.getEmail();
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // Check if User is employee
//        Employee employee = employeeService.getEmployeeByEmail(email);
//        Customer customer = customerService.getCustomerByEmail(email);
        Employee employee = employeeService.getEmployeeByUsername(username);
        Customer customer = customerService.getCustomerByUsername(username);
        if(employee != null && customer == null) { // is_employee
            is_verified = employeeService.verifyEmployeeByPassword(employee, password);
        } else if(employee == null && customer != null){ // is_customer
            is_verified = customerService.verifyCustomerByPassword(customer, password);
        } else { // none
            is_verified = false;
        }

        if(is_verified && employee != null) { // returns employee
            user = employee;
        } else if(is_verified && customer != null) { // returns customer
            user = customer;
        }

        return user;
    }

    public boolean checkForDuplicateUsername(String username) {
        boolean duplicated = true;
        if (employeeService.getEmployeeByUsername(username) == null &&
                customerService.getCustomerByUsername(username) == null ){
            duplicated = false;
        }

        return duplicated;
    }

}
