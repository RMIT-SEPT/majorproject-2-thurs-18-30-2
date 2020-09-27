package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.EmployeeSchedule;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This is the form(class) that is used to hold the list of employee schedules
 * for getEmployeeAvailability() method in EmployeeScheduleService class
 */
public class EmployeeAvailabilityForm {

    private Long employeeScheduleId;
    private String bServiceName;
    private String date;
    private Date startTime;
    private Date endTime;

    public EmployeeAvailabilityForm(EmployeeSchedule employeeSchedule) {
        this.employeeScheduleId = employeeSchedule.getId();
        this.bServiceName = employeeSchedule.getBService().getName();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        this.date = sdf.format(employeeSchedule.getDate());

        this.startTime = employeeSchedule.getStartTime();
        this.endTime = employeeSchedule.getEndTime();
    }

    public Long getEmployeeScheduleId() {
        return employeeScheduleId;
    }

    public void setEmployeeScheduleId(Long employeeScheduleId) {
        this.employeeScheduleId = employeeScheduleId;
    }

    public String getbServiceName() {
        return bServiceName;
    }

    public void setbServiceName(String bServiceName) {
        this.bServiceName = bServiceName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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
