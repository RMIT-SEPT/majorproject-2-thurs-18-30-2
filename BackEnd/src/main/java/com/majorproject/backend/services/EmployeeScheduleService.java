package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.EmployeeScheduleRepository;
import com.majorproject.backend.repositories.ServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Map;

@Service
public class EmployeeScheduleService {
    @Autowired
    EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    ServiceRepository serviceRepository;

    public EmployeeSchedule saveOrUpdateEmployee(Map<String, String> request) {
        EmployeeSchedule employeeScheduleNew = null;
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");

        try {
            EmployeeSchedule employeeSchedule = new EmployeeSchedule();
            employeeSchedule.setDate(formatterDate.parse(request.get("date")));
            employeeSchedule.setStartTime(formatterTime.parse(request.get("startTime")));
            employeeSchedule.setEndTime(formatterTime.parse(request.get("endTime")));
            employeeSchedule.setEmployee(employeeRepository.findById(Long.parseLong(request.get("employee"))).get());
            employeeSchedule.setService(serviceRepository.findById(Long.parseLong(request.get("service"))).get());
            employeeScheduleNew = employeeScheduleRepository.save(employeeSchedule);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Scheduling error");
        }

        return employeeScheduleNew;
    }
}
