package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.util.IdErrorService;
import com.majorproject.backend.util.ListEmptyErrorService;
import com.majorproject.backend.util.ObjectEmptyErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

    private ListEmptyErrorService listEmptyErrorService = new ListEmptyErrorService();
    private ObjectEmptyErrorService objectEmptyErrorService = new ObjectEmptyErrorService();
    private IdErrorService idErrorService = new IdErrorService();

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

    public Employee getEmployeeById(String idAPI) {
        Long employeeId = idErrorService.idStringToLong(idAPI);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        objectEmptyErrorService.checkIfNull(employee, "Employee does not exist");

        return employee;
    }

    /**
     * Returns a list that contains all employees
     * @return A list that contains all employees
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAllEmployees();
        listEmptyErrorService.checkListEmpty(employeeList, "Employee");

        return employeeList;
    }

    public Employee editEmployee(String idAPI, Employee employee) {
        Long employeeId = idErrorService.idStringToLong(idAPI);
        Employee employeeEdit = employeeRepository.findByEmployeeId(employeeId);

        // Seting employee details
        employeeEdit.setfName(employee.getfName());
        employeeEdit.setlName(employee.getlName());
        employeeEdit.setEmail(employee.getEmail());
        employeeEdit.setAddress(employee.getAddress());
        employeeEdit.setUsername(employee.getUsername());
        employeeEdit.setPassword(employee.getPassword());
        employeeEdit.setpNumber(employee.getpNumber());

        saveOrUpdateEmployee(employeeEdit);

        return employeeEdit;
    }
}
