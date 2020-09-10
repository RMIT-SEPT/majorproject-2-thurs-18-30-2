package com.majorproject.backend.web;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/customer")
@CrossOrigin
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
        customerService.saveOrUpdateCustomer(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginCustomer(@RequestBody LoginForm loginForm) {
//        ResponseEntity<?> responseEntity = null;
//        Customer customer = customerService.loginCustomer(loginForm);
//        if(customer != null) {
//            responseEntity = new ResponseEntity<Customer>(customer, HttpStatus.OK);
//        } else {
//            responseEntity = new ResponseEntity<String>("User or Password invalid", HttpStatus.UNAUTHORIZED);
//        }
//
//        return responseEntity;
//    }
}
