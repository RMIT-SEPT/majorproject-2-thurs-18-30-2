package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public void saveOrUpdateCustomer(Customer customer) {
        customerRepository.save(customer);
    }

//    /* Logic */
//    public Customer loginCustomer(LoginForm loginForm) {
//        boolean login_success = false;
//        String email = loginForm.getEmail();
//        Customer customer = getCustomerByEmail(email);
//
//        if(customer != null) {
//            String password = loginForm.getPassword();
//            boolean is_verified = verifyCustomerByPassword(customer, password);
//
//            if(is_verified) {
//                login_success = true;
//            } else {
//                customer = null;
//            }
//        }
//
//        return customer;
//    }

//    public Customer getCustomerByEmail(String email) {
//        return customerRepository.findByEmail(email);
//    }
//
//    public Customer getCustomerByUsername(String username) {
//        return customerRepository.findByUsername(username);
//    }
//
//    public boolean verifyCustomerByPassword(Customer customer, String password) {
//        boolean is_verified = false;
//        if(customer.getPassword().equals(password)) {
//            is_verified = true;
//        }
//
//        return is_verified;
//    }
}
