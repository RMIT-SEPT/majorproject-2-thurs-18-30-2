package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.util.IdErrorUtil;
import com.majorproject.backend.util.ListEmptyErrorUtil;
import com.majorproject.backend.util.ObjectEmptyErrorUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private ListEmptyErrorUtil listEmptyErrorUtil = new ListEmptyErrorUtil();
    private ObjectEmptyErrorUtil objectEmptyErrorUtil = new ObjectEmptyErrorUtil();
    private IdErrorUtil idErrorUtil = new IdErrorUtil();

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
                employee.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
                employee.setEmpType("employee");
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
     * Gets the employee based on the employee id
     * @param idAPI The employee id
     * @return An employee if it exists
     */
    public Employee getEmployeeById(String idAPI) {
        Long employeeId = idErrorUtil.idStringToLong(idAPI);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);

        objectEmptyErrorUtil.checkIfNull(employee, "Employee does not exist");

        return employee;
    }

    /**
     * Returns a list that contains all employees
     * @return A list that contains all employees
     */
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = employeeRepository.findAllEmployees();
        listEmptyErrorUtil.checkListEmpty(employeeList, "Employee");

        return employeeList;
    }

    /**
     * Edits the employee
     * @param idAPI The employee id
     * @param employee The edited employee details
     * @return The edited employee, if successful
     */
    public Employee editEmployee(String idAPI, Employee employee) {
        Long employeeId = idErrorUtil.idStringToLong(idAPI);
        Employee employeeEdit = employeeRepository.findByEmployeeId(employeeId);

        // Setting employee details
        employeeEdit.setfName(employee.getfName());
        employeeEdit.setlName(employee.getlName());
        employeeEdit.setEmail(employee.getEmail());
        employeeEdit.setAddress(employee.getAddress());
        employeeEdit.setUsername(employee.getUsername());
        if(employee.getPassword().length() != 0) {
            employeeEdit.setPassword(bCryptPasswordEncoder.encode(employee.getPassword()));
        }
        employeeEdit.setpNumber(employee.getpNumber());
        employeeRepository.save(employeeEdit);

        return employeeEdit;
    }
}
