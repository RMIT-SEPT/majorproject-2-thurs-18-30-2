package com.majorproject.backend.web;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.services.CustomerService;
import com.majorproject.backend.services.MapValidationErrorService;
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

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerCustomer(@Valid @RequestBody Customer customer, BindingResult result) {
        ResponseEntity<?> response;

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null) {
            response = errorMap;
        } else {
            Customer customerNew = customerService.saveOrUpdateCustomer(customer);
            response = new ResponseEntity<Customer>(customerNew, HttpStatus.CREATED);
        }

        return response;
    }

    @PostMapping("/editCustomer/{username}")
    public ResponseEntity<?> editCustomer(@Valid @PathVariable String username, @RequestBody Customer customer, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if(errorMap != null) {
            response = errorMap;
        } else {
            Customer customerEdit = customerService.getCustomerByUsername(username);

            // Seting customer details
            customerEdit.setfName(customer.getfName());
            customerEdit.setlName(customer.getlName());
            customerEdit.setEmail(customer.getEmail());
            customerEdit.setAddress(customer.getAddress());
            customerEdit.setUsername(customer.getUsername());
            customerEdit.setPassword(customer.getPassword());
            customerEdit.setpNumber(customer.getpNumber());

            customerService.saveOrUpdateCustomer(customerEdit);
            response = new ResponseEntity<Customer>(customerEdit, HttpStatus.OK);
        }

        return response;
    }
}
