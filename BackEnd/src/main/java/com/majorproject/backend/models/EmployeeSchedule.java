package com.majorproject.backend.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class EmployeeSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_schedule_id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_id")
    @NotNull(message = "Employee Id required")
    private Employee employee;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    @NotNull(message = "Service Id required")
    private BService bService;

    @Basic
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Date required")
    private Date date;

    @Basic
    @Temporal(TemporalType.TIME)
    @NotNull(message = "Start time required")
    private Date startTime;

    @Basic
    @Temporal(TemporalType.TIME)
//    @Temporal(TemporalType.DATE)
    @NotNull(message = "End time required")
    private Date endTime;
    @Column(name = "availability")
    private boolean availability;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public BService getBService() {
        return bService;
    }

    public void setBService(BService bService) {
        this.bService = bService;
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

    public boolean getAvailability() {
        return availability;
    }

    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
}
