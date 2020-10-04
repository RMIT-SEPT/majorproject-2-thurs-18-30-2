package com.majorproject.backend.services;

import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;

import java.util.Date;
import java.util.List;

public class ListWithTimeboundService {
    private List<EmployeeScheduleAvailabilityForm> list;
    private Date start;
    private Date end;

    public ListWithTimeboundService(List<EmployeeScheduleAvailabilityForm> list) {
        this.list = list;

        this.start = list.get(0).getScheduleStartTime();
        this.end = list.get(0).getScheduleEndTime();

        for(int i = 1; i < list.size(); ++i) {
            if(start.after(list.get(i).getScheduleStartTime())) {
                start = list.get(i).getScheduleStartTime();
            }

            if(end.before(list.get(i).getScheduleEndTime())) {
                end = list.get(i).getScheduleEndTime();
            }
        }
    }

    public List<EmployeeScheduleAvailabilityForm> getList() {
        return list;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }
}
