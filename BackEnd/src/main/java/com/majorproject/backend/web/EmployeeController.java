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

    /**
     * Registers an employee
     * @param employee The employee
     * @param result BindingResult
     * @return A response entity of the employee
     */
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

    /**
     * Edits the details of an employee
     * @param username The employee's username
     * @param employee The employee
     * @param result BindingResult
     * @return A response entity of the employee with the updated details
     */
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
            employeeEdit.setUsername(employee.getUsername());
            employeeEdit.setPassword(employee.getPassword());
            employeeEdit.setpNumber(employee.getpNumber());

            employeeService.saveOrUpdateEmployee(employeeEdit);
            response = new ResponseEntity<Employee>(employeeEdit, HttpStatus.OK);
        }

        return response;
    }

    /**
     * Finds the employee by the username
     * @param username The employee's username
     * @return A response entity of the employee based on the username
     */
    @GetMapping("/getEmployeeByUsername/{username}")
    public ResponseEntity<?> getEmployeeByUsername(@Valid @PathVariable String username) {
        Employee employee = employeeService.getEmployeeByUsername(username);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    /**
     * Finds the employee by the id
     * @param id The employee's id
     * @return A response entity of the employee based on the id
     */
    @GetMapping("/getEmployeeById/{id}")
    public ResponseEntity<?> getEmployeeById(@Valid @PathVariable String id) {
        long employeeId = Long.parseLong(id);
        Employee employee = employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<Employee>(employee, HttpStatus.OK);
    }

    /**
     * Gets the list of all employees in the database
     * @return A response entity of a list of employees in the database
     */
    @GetMapping("/getAllEmployees")
    public ResponseEntity<?> getAllEmployees() {
        List<Employee> employeeList = employeeService.getAllEmployees();
        return new ResponseEntity<List<Employee>>(employeeList, HttpStatus.OK);
    }
}