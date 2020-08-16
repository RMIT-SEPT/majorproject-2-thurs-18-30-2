package com.majorproject.backend.web;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/customer")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    @PostMapping("")
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer customer) {
//        if (result.hasErrors()){
//            return new ResponseEntity<String>("Invalid Person Object", HttpStatus.BAD_REQUEST);
//        }
        Customer customerNew = customerService.saveOrUpdateCustomer(customer);
        return new ResponseEntity<Customer>(customer, HttpStatus.CREATED);
    }

    @GetMapping("/get/email/{email}")
    public ResponseEntity<?> getCustomerById(@PathVariable String email) {
        Customer customer = customerService.getCustomerByEmail(email);
        return new ResponseEntity<Customer>(customer, HttpStatus.OK);
    }
}
