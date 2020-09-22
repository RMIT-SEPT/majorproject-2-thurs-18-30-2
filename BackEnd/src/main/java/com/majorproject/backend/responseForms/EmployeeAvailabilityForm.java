package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.EmployeeSchedule;

import java.util.Date;

public class EmployeeAvailabilityForm {

    private Long employeeScheduleId;
    private Date date;
    private Date startTime;
    private Date endTime;

    public EmployeeAvailabilityForm(EmployeeSchedule employeeSchedule) {
        this.employeeScheduleId = employeeSchedule.getId();
        this.date = employeeSchedule.getDate();
        this.startTime = employeeSchedule.getStartTime();
        this.endTime = employeeSchedule.getEndTime();
    }

    public Long getEmployeeScheduleId() {
        return employeeScheduleId;
    }

    public void setEmployeeScheduleId(Long employeeScheduleId) {
        this.employeeScheduleId = employeeScheduleId;
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
