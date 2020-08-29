package com.majorproject.backend.services;

import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Customer saveOrUpdateCustomer(Customer person) {
        return customerRepository.save(person);
    }

    public Customer getCustomerByEmail(String email) {
        Customer customer = customerRepository.findByEmail(email);

        return customer;
    }

    public boolean verifyCustomerByPassword(Customer customer, String password) {
        boolean is_verified = false;
        if(customer.getPassword().equals(password)) {
            is_verified = true;
        }

        return is_verified;
    }
}
