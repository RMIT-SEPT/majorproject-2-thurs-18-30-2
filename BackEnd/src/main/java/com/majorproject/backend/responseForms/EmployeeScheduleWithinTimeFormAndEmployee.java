package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.EmployeeSchedule;

/**
 * This is the form(class) that is used to hold the list of employee schedules
 * for getServicesWithinParameters() method in EmployeeScheduleService class
 */
public class EmployeeScheduleWithinTimeFormAndEmployee {

    private Long employeeScheduleId;
    private Long employeeId;
    private String employeeFName;
    private String employeeLName;
    private String bServiceName;

    public EmployeeScheduleWithinTimeFormAndEmployee(EmployeeSchedule employeeSchedule) {
        this.employeeId = employeeSchedule.getEmployee().getId();
        this.employeeScheduleId = employeeSchedule.getId();
        this.employeeFName = employeeSchedule.getEmployee().getfName();
        this.employeeLName = employeeSchedule.getEmployee().getlName();
        this.bServiceName = employeeSchedule.getBService().getName();
    }

    public Long getEmployeeScheduleId() {
        return employeeScheduleId;
    }

    public void setEmployeeScheduleId(Long employeeScheduleId) {
        this.employeeScheduleId = employeeScheduleId;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
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

    public String getbServiceName() {
        return bServiceName;
    }

    public void setbServiceName(String bServiceName) {
        this.bServiceName = bServiceName;
    }
}
