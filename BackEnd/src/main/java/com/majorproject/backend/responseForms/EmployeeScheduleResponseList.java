package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.EmployeeSchedule;

import java.util.List;

public class EmployeeScheduleResponseList {
    private List<EmployeeScheduleResponseObject> employeeSchedules;
    public EmployeeScheduleResponseList(List<EmployeeSchedule> employeeSchedules) {
        for(int i = 0; i != employeeSchedules.size(); ++i) {
            this.employeeSchedules.add(new EmployeeScheduleResponseObject(employeeSchedules.get(i)));
        }
    }

    public List<EmployeeScheduleResponseObject> getEmployeeSchedules() {
        return employeeSchedules;
    }

    public void setEmployeeSchedules(List<EmployeeScheduleResponseObject> employeeSchedules) {
        this.employeeSchedules = employeeSchedules;
    }
}
