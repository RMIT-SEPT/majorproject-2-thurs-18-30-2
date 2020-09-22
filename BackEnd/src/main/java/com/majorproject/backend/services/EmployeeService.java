package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    /**
     * Creates an employee and saves it to the database
     * @param employee The employee
     * @return The employee
     */
    public Employee saveOrUpdateEmployee(Employee employee) {
        Employee employeeNew = null;
        String username = employee.getUsername();

        if(customerRepository.findByUsername(username) == null) {
            try {
                employeeNew = employeeRepository.save(employee);
            } catch(Exception e) {
                throw new ResponseException(HttpStatus.BAD_REQUEST, "Username already in use");
            }
        } else {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Username already in use");
        }

        return employeeNew;
    }

    /**
     * Returns the employee based on the username
     * @param username The username specified by the user
     * @return The employee if the employee with that username exists
     */
    public Employee getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if(employee == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Employee does not exist");
        }

        return employee;
    }

    /**
     * Returns the employee based on the employeeId
     * @param employeeId The employeeId specified by the user
     * @return The employee if the employee with that employeeId exists
     */
    public Employee getEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if(employee == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Employee does not exist");
        }

        return employee;
    }

    /**
     * Returns a list that contains all employees
     * @return A list that contains all employees
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAllEmployees();
        if(employeeList.size() == 0 || employeeList == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "No employees exist");
        }

        return employeeList;
    }
}
