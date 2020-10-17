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

    /**
     * Registers the customer
     * @param customer The customer
     * @param result BindingResult
     * @return A response entity that returns the customer when it is registered
     */
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

    /**
     * Edits the customer based on the customerId
     * @param idAPI The customerId
     * @param customer The edited custoemr details
     * @param result BindingResult
     * @return The edited customer, if successful
     */
    @PostMapping("/editCustomer/{idAPI}")
    public ResponseEntity<?> editCustomer(@Valid @PathVariable String idAPI, @RequestBody Customer customer, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if(errorMap != null) {
            response = errorMap;
        } else {
            Customer customerEdit = customerService.editCustomer(idAPI, customer);
            response = new ResponseEntity<Customer>(customerEdit, HttpStatus.OK);
        }

        return response;
    }
}
