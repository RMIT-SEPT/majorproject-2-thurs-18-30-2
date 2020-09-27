package com.majorproject.backend.responseForms;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;

/**
 * Class not used
 */
public class EmployeeResponseObject {
    private String lName, fName;
    private Long id;
    public EmployeeResponseObject(Employee employee) {
        lName = employee.getlName();
        fName = employee.getfName();
        id = employee.getId();
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
