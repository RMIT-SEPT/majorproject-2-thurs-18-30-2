package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * Creates a customer and adds it to the database
     * @param customer The customer
     * @return The customer
     */
    public Customer saveOrUpdateCustomer(Customer customer) {
        Customer customerNew = null;
        String username = customer.getUsername();

        if(employeeRepository.findByUsername(username) == null) {
            try {
                customerNew = customerRepository.save(customer);
            } catch(Exception e) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, "Username already in use");
            }
        } else {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        return customerNew;
    }

    /**
     * Returns the customer based on the username
     * @param username The username specified by the user
     * @return The customer if the customer with that username exists
     */
    public Customer getCustomerByUsername(String username) {
        return customerRepository.findByUsername(username);
    }
}
