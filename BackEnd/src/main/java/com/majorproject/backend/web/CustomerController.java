package com.majorproject.backend.web;

import com.majorproject.backend.jsonconv.LoginForm;
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
@CrossOrigin
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

    @PostMapping("/verify")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginForm loginForm) {
        ResponseEntity<?> responseEntity = null;
        Customer customer = customerService.getCustomerByEmail(loginForm.getEmail());
        if(customer == null) {
            responseEntity = new ResponseEntity<String>("User does not exist", HttpStatus.NOT_FOUND);
        }
        else {
            if(customer.getPassword().equals(loginForm.getPassword())) {
                responseEntity = new ResponseEntity<Customer>(customer, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<String>("Password invalid", HttpStatus.UNAUTHORIZED);
            }
        }

        return responseEntity;
    }

}
