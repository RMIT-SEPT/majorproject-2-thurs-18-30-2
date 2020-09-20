package com.majorproject.backend.web;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.services.EmployeeService;
import com.majorproject.backend.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/employee")
@CrossOrigin
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@Valid @RequestBody Employee employee, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if (errorMap != null) {
            response = errorMap;
        } else {
            Employee employeeNew = employeeService.saveOrUpdateEmployee(employee);
            response = new ResponseEntity<Employee>(employee, HttpStatus.CREATED);
        }

        return response;
    }

    @PostMapping("/editEmployee/{username}")
    public ResponseEntity<?> editEmployee(@Valid @PathVariable String username, @RequestBody Employee employee, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if(errorMap != null) {
            response = errorMap;
        } else {
            Employee employeeEdit = employeeService.getEmployeeByUsername(username);

            // Seting employee details
            employeeEdit.setfName(employee.getfName());
            employeeEdit.setlName(employee.getlName());
            employeeEdit.setEmail(employee.getEmail());
            employeeEdit.setAddress(employee.getAddress());
            employeeEdit.setPassword(employee.getPassword());
            employeeEdit.setpNumber(employee.getpNumber());

            employeeService.saveOrUpdateEmployee(employeeEdit);
            response = new ResponseEntity<Employee>(employeeEdit, HttpStatus.OK);
        }

        return response;
    }

    /* Testing Purposes */
    @GetMapping("/test/getAllEmployees")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/test/getEmployeeByUsername")
    public Employee getEmployeeByUsername(String username) { return employeeService.getEmployeeByUsername(username); }
}