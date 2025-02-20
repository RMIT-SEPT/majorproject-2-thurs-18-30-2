package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.BService;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.util.DateErrorUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * This class is used in EmployeeScheduleService to create a custom list
 */
public class EmpSchByEmpIdForm {

    private DateErrorUtil dateErrorUtil = new DateErrorUtil();

    // From EmployeeSchedule
    private long employeeScheduleId;
    private List<String> startDate;
    private List<String> endDate;

    // From BService
    private long bServiceId;
    private String bServiceName;

    public EmpSchByEmpIdForm(EmployeeSchedule employeeSchedule) {
        this.employeeScheduleId = employeeSchedule.getId();
        Date scheduleDate = employeeSchedule.getDate();
        Date scheduleStartTime = employeeSchedule.getStartTime();
        Date scheduleEndTime = employeeSchedule.getEndTime();

        this.startDate = runConstructorDate(scheduleDate, scheduleStartTime);
        this.endDate = runConstructorDate(scheduleDate, scheduleEndTime);

        BService bService = employeeSchedule.getBService();
        this.bServiceId = bService.getId();
        this.bServiceName = bService.getName();
    }

    public List<String> runConstructorDate(Date scheduleDate, Date scheduleTime) {
        List<String> list = new ArrayList<String>();

        String dateInString = dateErrorUtil.convertToStringType(scheduleDate, "date");
        String timeInString = dateErrorUtil.convertToStringType(scheduleTime, "time");

        List<String> dateList = Arrays.asList(dateInString.split("-"));

        // Remove 0 from Month if Month is before October (for Front End ease)
        if(dateList.get(1).substring(0, 1).equals("0")) {
            dateList.set(1, dateList.get(1).substring(1, 2));
        }

        List<String> timeList = Arrays.asList(timeInString.split(":"));

        list.addAll(dateList);
        list.addAll(timeList);

        return list;
    }

    public long getEmployeeScheduleId() {
        return employeeScheduleId;
    }

    public void setEmployeeScheduleId(long employeeScheduleId) {
        this.employeeScheduleId = employeeScheduleId;
    }

    public List<String> getStartDate() {
        return startDate;
    }

    public void setStartDate(List<String> startDate) {
        this.startDate = startDate;
    }

    public List<String> getEndDate() {
        return endDate;
    }

    public void setEndDate(List<String> endDate) {
        this.endDate = endDate;
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
