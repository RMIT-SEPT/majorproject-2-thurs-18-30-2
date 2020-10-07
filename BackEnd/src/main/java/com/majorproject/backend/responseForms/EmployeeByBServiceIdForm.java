package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;

public class EmployeeByBServiceIdForm {

    // From Employee
    private long employeeId;
    private String employeeFName;
    private String employeeLName;

    public EmployeeByBServiceIdForm(EmployeeSchedule employeeSchedule) {
        Employee employee = employeeSchedule.getEmployee();
        this.employeeId = employee.getId();
        this.employeeFName = employee.getfName();
        this.employeeLName = employee.getlName();
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
}
