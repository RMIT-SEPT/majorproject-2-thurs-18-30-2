package com.majorproject.backend.responseForms;

import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;

import java.util.Date;
import java.util.List;

/**
 * This class is used by EmployeeScheduleService to create a custom object (but it is technically still a list)
 */
public class ListWithTimeboundForm {
    private List<EmployeeScheduleAvailabilityForm> list;
    private Date start;
    private Date end;

    public ListWithTimeboundForm(List<EmployeeScheduleAvailabilityForm> list) {
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
