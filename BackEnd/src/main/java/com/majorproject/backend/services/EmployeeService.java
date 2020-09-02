package com.majorproject.backend.services;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveOrUpdatePerson(Employee employee) {
        return employeeRepository.save(employee);
    }

    /* Logic */
    public Employee loginEmployee(LoginForm loginForm) {
        boolean login_success = false;
        String email = loginForm.getEmail();
        Employee employee = getEmployeeByEmail(email);

        if(employee != null) {
            String password = loginForm.getPassword();
            boolean is_verified = verifyEmployeeByPassword(employee, password);

            if(is_verified) {
                login_success = true;
            } else {
                employee = null;
            }
        }

        return employee;
    }

    /* Used Methods */
    public Employee getEmployeeByEmail(String email) {
        Employee employee = employeeRepository.findByEmail(email);

        return employee;
    }

    public boolean verifyEmployeeByPassword(Employee employee, String password) {
        boolean is_verified = false;
        if(employee.getPassword().equals(password)) {
            is_verified = true;
        }

        return is_verified;
    }
}
