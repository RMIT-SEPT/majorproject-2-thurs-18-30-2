package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public void saveOrUpdateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    /* Testing purposes */
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeByUsername(String username) { return employeeRepository.findByUsername(username); }

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
