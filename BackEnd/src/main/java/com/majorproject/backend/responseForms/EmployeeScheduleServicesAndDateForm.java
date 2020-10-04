package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.models.EmployeeSchedule;

import java.util.Date;

public class EmployeeScheduleServicesAndDateForm {

    // From Employee Schedule
    private Date scheduleDate;
    private Date scheduleStartTime;

    // From BService
    private long bServiceId;
    private String bServiceName;

    public EmployeeScheduleServicesAndDateForm(EmployeeSchedule employeeSchedule) {
        this.scheduleDate = employeeSchedule.getDate();
        this.scheduleStartTime = employeeSchedule.getStartTime();

        BService bService = employeeSchedule.getBService();
        this.bServiceId = bService.getId();
        this.bServiceName = bService.getName();
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
