package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the form(class) that is used to hold the list of employee schedules
 * It takes in an employee schedule and return only the following
 * employee schedule id, schedule date, schedule start time, schedule end time
 * employee id, employee first name, employee last name
 * bservice id, bservice name
 * date, schedule start time, schedule end time
 */
public class EmployeeScheduleAvailabilityForm {

    // From Employee Schedule
    private long employeeScheduleId;
    private Date scheduleDate;
    private Date scheduleStartTime;
    private Date scheduleEndTime;

    // From Employee
    private long employeeId;
    private String employeeFName;
    private String employeeLName;

    // From BService
    private long bServiceId;
    private String bServiceName;

    public EmployeeScheduleAvailabilityForm(EmployeeSchedule employeeSchedule) {
        this.employeeScheduleId = employeeSchedule.getId();
        this.scheduleDate = employeeSchedule.getDate();
        this.scheduleStartTime = employeeSchedule.getStartTime();
        this.scheduleEndTime = employeeSchedule.getEndTime();

        Employee employee = employeeSchedule.getEmployee();
        this.employeeId = employee.getId();
        this.employeeFName = employee.getfName();
        this.employeeLName = employee.getlName();

        BService bService = employeeSchedule.getBService();
        this.bServiceId = bService.getId();
        this.bServiceName = bService.getName();
    }

    public long getEmployeeScheduleId() {
        return employeeScheduleId;
    }

    public void setEmployeeScheduleId(long employeeScheduleId) {
        this.employeeScheduleId = employeeScheduleId;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public Date getScheduleStartTime() {
        return scheduleStartTime;
    }

    public void setScheduleStartTime(Date scheduleStartTime) {
        this.scheduleStartTime = scheduleStartTime;
    }

    public Date getScheduleEndTime() {
        return scheduleEndTime;
    }

    public void setScheduleEndTime(Date scheduleEndTime) {
        this.scheduleEndTime = scheduleEndTime;
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

    public long getbServiceId() {
        return bServiceId;
    }

    public void setbServiceId(long bServiceId) {
        this.bServiceId = bServiceId;
    }

    public String getbServiceName() {
        return bServiceName;
    }

    public void setbServiceName(String bServiceName) {
        this.bServiceName = bServiceName;
    }
}
