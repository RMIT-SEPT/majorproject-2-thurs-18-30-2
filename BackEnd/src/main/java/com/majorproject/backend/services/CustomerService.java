package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.util.IdErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private IdErrorUtil idErrorUtil = new IdErrorUtil();

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
                customer.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
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

    public Customer editCustomer(String idAPI, Customer customer) {
        Long customerId = idErrorUtil.idStringToLong(idAPI);
        Customer customerEdit = customerRepository.findByCustomerId(customerId);

        // Setting customer details
        customerEdit.setfName(customer.getfName());
        customerEdit.setlName(customer.getlName());
        customerEdit.setEmail(customer.getEmail());
        customerEdit.setAddress(customer.getAddress());
        customerEdit.setUsername(customer.getUsername());
        if(customer.getPassword().length() != 0) customerEdit.setPassword(bCryptPasswordEncoder.encode(customer.getPassword()));
        customerEdit.setpNumber(customer.getpNumber());

        customerRepository.save(customerEdit);

        return customerEdit;
    }
}
