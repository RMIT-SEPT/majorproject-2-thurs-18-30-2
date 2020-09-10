package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.User;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public User loginUser(LoginForm loginForm) {
        User user = null;
//        boolean is_verified = false;
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();

        // Check if user is employee or customer or none
        Employee employee = employeeRepository.findByUsername(username);
        Customer customer = customerRepository.findByUsername(username);
//        Employee employee = employeeService.getEmployeeByUsername(username);
//        Customer customer = customerService.getCustomerByUsername(username);
        if(employee != null) { // is_employee
            if(password.equals(employee.getPassword())) {
                user = employee;
            }
//            is_verified = employeeService.verifyEmployeeByPassword(employee, password);
        } else if(customer != null){ // is_customer
            if(password.equals(customer.getPassword())) {
                user = customer;
            }
//            is_verified = customerService.verifyCustomerByPassword(customer, password);
        }
//        else { // none
//            is_verified = false;
//        }

//        if(is_verified && employee != null) { // returns employee
//            user = employee;
//        } else if(is_verified && customer != null) { // returns customer
//            user = customer;
//        }

        return user;
    }

    public boolean checkForDuplicateUsername(String username) {
        boolean duplicated = true;
        if (employeeRepository.findByUsername(username) == null &&
                customerRepository.findByUsername(username) == null ){
            duplicated = false;
        }

        return duplicated;
    }

}
