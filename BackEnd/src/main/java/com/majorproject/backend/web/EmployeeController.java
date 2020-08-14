package com.majorproject.backend.web;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        if(result.hasErrors()) {
            return new ResponseEntity<String>("Invalid Person Object", HttpStatus.BAD_REQUEST);
        }
        Employee employeeNew = employeeService.saveOrUpdatePerson(employee);
        return new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
    }
}
