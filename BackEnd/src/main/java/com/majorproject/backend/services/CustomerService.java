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

        //logic
        return customerRepository.save(person);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}
