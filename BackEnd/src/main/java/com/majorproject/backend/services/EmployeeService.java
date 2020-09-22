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

    public Employee getEmployeeByUsername(String username) {
        Employee employee = employeeRepository.findByUsername(username);
        if(employee == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Employee does not exist");
        }

        return employee;
    }

    public Employee getEmployeeById(long employeeId) {
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        if(employee == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Employee does not exist");
        }

        return employee;
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAllEmployees();
        if(employeeList.size() == 0 || employeeList == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "No employees exist");
        }

        return employeeList;
    }

//    /* Logic */
//    public Employee loginEmployee(LoginForm loginForm) {
//        boolean login_success = false;
//        String email = loginForm.getEmail();
//        Employee employee = getEmployeeByEmail(email);
//
//        if(employee != null) {
//            String password = loginForm.getPassword();
//            boolean is_verified = verifyEmployeeByPassword(employee, password);
//
//            if(is_verified) {
//                login_success = true;
//            } else {
//                employee = null;
//            }
//        }
//
//        return employee;
//    }

//    public Employee getEmployeeByEmail(String email) {
//        return employeeRepository.findByEmail(email);
//    }
//
//    public Employee getEmployeeByUsername(String username) {
//        return employeeRepository.findByUsername(username);
//    }
//
//    public boolean verifyEmployeeByPassword(Employee employee, String password) {
//        boolean is_verified = false;
//        if(employee.getPassword().equals(password)) {
//            is_verified = true;
//        }
//
//        return is_verified;
//    }
}
