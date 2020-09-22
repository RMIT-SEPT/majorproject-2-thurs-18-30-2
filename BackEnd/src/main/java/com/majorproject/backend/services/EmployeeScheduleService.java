package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.EmployeeScheduleRepository;
import com.majorproject.backend.repositories.BServicesRepository;
import com.majorproject.backend.responseForms.EmployeeAvailabilityForm;
import com.majorproject.backend.responseForms.EmployeeScheduleWithinTimeForm;
import com.majorproject.backend.responseForms.EmployeeScheduleWithinTimeFormAndEmployee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class EmployeeScheduleService {
    @Autowired
    EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    BServicesRepository BServicesRepository;

    public EmployeeSchedule saveEmployeeSchedule(Map<String, String> request) {
        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
        EmployeeSchedule employeeScheduleNew = null;
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");

        try {
            employeeSchedule.setDate(formatterDate.parse(request.get("date")));
            employeeSchedule.setStartTime(formatterTime.parse(request.get("startTime")));
            employeeSchedule.setEndTime(formatterTime.parse(request.get("endTime")));
//            employeeSchedule.setEmployee(employeeRepository.findById(Long.parseLong(request.get("employee"))));
            employeeSchedule.setEmployee(employeeRepository.findByEmployeeId(Long.parseLong(request.get("employeeId"))));
//            employeeSchedule.setBService(BServicesRepository.findById(Long.parseLong(request.get("bService"))).get());
            employeeSchedule.setBService(BServicesRepository.findByBServiceId(Long.parseLong(request.get("bServiceId"))));
//            employeeScheduleNew = employeeScheduleRepository.save(employeeSchedule);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Scheduling error");
        }

        boolean anyDuplicates = findAnyDuplicates(employeeSchedule);
        if(anyDuplicates) { // there is a duplicate
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
        } else { // No duplicate
            employeeScheduleNew = employeeScheduleRepository.save(employeeSchedule);
        }

        return employeeScheduleNew;
    }

    /**
     *Find any duplicated employee schedule in the database
     * If there are duplicates, return true
     * If there are no duplicates, return false
     */
    public boolean findAnyDuplicates(EmployeeSchedule employeeSchedule) {
        boolean found = false;
        List<EmployeeSchedule> employeeScheduleList = employeeScheduleRepository.getAllEmployeeSchedules();

        // Checks all variables
        for(int i = 0; i < employeeScheduleList.size() && !found; ++i) {
            if(employeeSchedule.getEmployee().getId().equals(employeeScheduleList.get(i).getEmployee().getId()) &&
            employeeSchedule.getBService().getId().equals(employeeScheduleList.get(i).getBService().getId()) &&
            employeeSchedule.getDate().equals(employeeScheduleList.get(i).getDate()) &&
            employeeSchedule.getStartTime().equals(employeeScheduleList.get(i).getStartTime()) &&
            employeeSchedule.getEndTime().equals(employeeScheduleList.get(i).getEndTime())) {
                found = true;
            }
        }

        return found;
    }

    public void updateEmployeeSchedule(EmployeeSchedule employeeSchedule) {
        //To be done
    }

    public EmployeeSchedule getEmployeeScheduleById(Long id) {
        return employeeScheduleRepository.getEmployeeScheduleById(id);
    }

    public List<EmployeeAvailabilityForm> getEmployeeAvailability(Long id) {
        List<EmployeeSchedule> employeeScheduleList = employeeScheduleRepository.getEmployeeAvailabilityById(id);
        List<EmployeeAvailabilityForm> employeeAvailability = new ArrayList<EmployeeAvailabilityForm>();

        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            employeeAvailability.add(new EmployeeAvailabilityForm(employeeScheduleList.get(i)));
        }

        if(employeeAvailability.size() == 0) {
            throw new ResponseException(HttpStatus.NO_CONTENT, "No employee available");
        }

        return employeeAvailability;
    }

    public List<Object> getServicesWithinParameters(Map<String, String> request) {
        boolean findAllEmployees = false;
        List<EmployeeSchedule> employeeScheduleList;
        List<Object> employeeScheduleTimeList = new ArrayList<Object>();
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");

        try {
            Long bServiceId = Long.parseLong(request.get("bServiceId"));
            Date date = formatterDate.parse(request.get("date"));
            Date startTime = formatterTime.parse(request.get("startTime"));
            Date endTime = formatterTime.parse(request.get("endTime"));

            if(request.get("employeeId") == null) { // find all employees
                employeeScheduleList = employeeScheduleRepository.findSchedulesWithinParameters(bServiceId, date, startTime, endTime);
                findAllEmployees = true;
            } else { // f ind a specific employee only
                Long employeeId = Long.parseLong(request.get("employeeId"));
                employeeScheduleList = employeeScheduleRepository.findSchedulesWithinParameters(bServiceId, employeeId, date, startTime, endTime);
            }
        }
        catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
        }

        if(employeeScheduleList.size() == 0) {
            throw new ResponseException(HttpStatus.NO_CONTENT, "No services available");
        }

        if(findAllEmployees) { // find all employees
            for(int i = 0; i < employeeScheduleList.size(); ++i) {
                employeeScheduleTimeList.add(new EmployeeScheduleWithinTimeForm(employeeScheduleList.get(i)));
            }
        } else { // find specific employee only
            for(int i = 0; i < employeeScheduleList.size(); ++i) {
                employeeScheduleTimeList.add(new EmployeeScheduleWithinTimeFormAndEmployee(employeeScheduleList.get(i)));
            }
        }

        return employeeScheduleTimeList;
    }
}
