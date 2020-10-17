package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;

/**
 * This class is used in EmployeeScheduleService to create a custom list
 */
public class EmployeeByBServiceIdForm {

    // From Employee
    private long employeeId;
    private String employeeFName;
    private String employeeLName;
    private String employeeUsername;

    public EmployeeByBServiceIdForm(EmployeeSchedule employeeSchedule) {
        Employee employee = employeeSchedule.getEmployee();
        this.employeeId = employee.getId();
        this.employeeFName = employee.getfName();
        this.employeeLName = employee.getlName();
        this.employeeUsername = employee.getUsername();
    }

    public long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(long employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeFName() {
        return employeeFName;
    }

    public void setEmployeeFName(String employeeFName) {
        this.employeeFName = employeeFName;
    }

    public String getEmployeeLName() {
        return employeeLName;
    }

    public void setEmployeeLName(String employeeLName) {
        this.employeeLName = employeeLName;
    }

    public String getEmployeeUsername() {
        return employeeUsername;
    }

    public void setEmployeeUsername(String employeeUsername) {
        this.employeeUsername = employeeUsername;
    }
}
