package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.models.BService;

import java.util.Date;

/**
 * Class not used
 */
public class EmployeeScheduleResponseObject {
    private EmployeeResponseObject employee;
    private BService BService;
    private Date date;
    private Date startTime;
    private Date endTime;

    public EmployeeScheduleResponseObject(EmployeeSchedule employeeSchedule) {
        this.employee = new EmployeeResponseObject(employeeSchedule.getEmployee());
        this.BService = employeeSchedule.getBService();
        this.date = employeeSchedule.getDate();
        this.startTime = employeeSchedule.getStartTime();
        this.endTime = employeeSchedule.getEndTime();
    }

    public EmployeeResponseObject getEmployee() {
        return employee;
    }

    public void setEmployee(EmployeeResponseObject employee) {
        this.employee = employee;
    }

    public BService getBService() {
        return BService;
    }

    public void setBService(BService BService) {
        this.BService = BService;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
