package com.majorproject.backend.services;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    public Employee saveOrUpdatePerson(Employee person) {
        //logic
        return employeeRepository.save(person);
    }
}
