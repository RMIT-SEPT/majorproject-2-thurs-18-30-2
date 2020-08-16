package com.majorproject.backend.web;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("api/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        Employee employeeNew = employeeService.saveOrUpdatePerson(employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> loginCustomer(@RequestBody LoginForm loginForm) {
        ResponseEntity<?> responseEntity = null;
        Employee employee = employeeService.getEmployeeByEmail(loginForm.getEmail());
        if(employee == null) {
            responseEntity = new ResponseEntity<String>("User does not exist", HttpStatus.NOT_FOUND);
        } else {
            if(employee.getPassword().equals(loginForm.getPassword())) {
                responseEntity = new ResponseEntity<Employee>(employee, HttpStatus.OK);
            } else {
                responseEntity = new ResponseEntity<String>("Email or password invalid", HttpStatus.UNAUTHORIZED);
            }
        }
        return responseEntity;
    }
}