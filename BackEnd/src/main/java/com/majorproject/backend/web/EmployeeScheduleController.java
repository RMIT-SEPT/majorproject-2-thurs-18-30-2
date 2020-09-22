package com.majorproject.backend.web;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.responseForms.EmployeeAvailabilityForm;
import com.majorproject.backend.responseForms.EmployeeScheduleWithinTimeForm;
import com.majorproject.backend.responseForms.EmployeeScheduleWithinTimeFormAndEmployee;
import com.majorproject.backend.services.EmployeeScheduleService;
import com.majorproject.backend.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/employeeSchedule")
@CrossOrigin
public class EmployeeScheduleController {

    @Autowired
    private EmployeeScheduleService employeeScheduleService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("/create")
    public ResponseEntity<?> createEmployeeSchedule(@Valid @RequestBody Map<String, String> request, BindingResult result) {
        ResponseEntity<?> response;

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null) {
            response = errorMap;
        } else {
            EmployeeSchedule employeeScheduleNew = employeeScheduleService.saveEmployeeSchedule(request);
            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleNew, HttpStatus.CREATED);
        }

        return response;

//        EmployeeSchedule employeeScheduleNew = employeeScheduleService.saveOrUpdateEmployeeSchedule(request);
//        return new ResponseEntity<EmployeeSchedule>(employeeScheduleNew, HttpStatus.CREATED);
    }

    @PostMapping("/editSchedule/{scheduleId}")
    public ResponseEntity<?> editEmployeeSchedule(@Valid @PathVariable String id, @RequestBody EmployeeSchedule employeeSchedule, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        Long scheduleId = Long.parseLong(id);

        if(errorMap != null) {
            response = errorMap;
        } else {
            EmployeeSchedule employeeScheduleEdit = employeeScheduleService.getEmployeeScheduleById(scheduleId);

            // Setting employee schedule details
            employeeScheduleEdit.setEmployee(employeeSchedule.getEmployee());
            employeeScheduleEdit.setBService(employeeSchedule.getBService());
            employeeScheduleEdit.setDate(employeeSchedule.getDate());
            employeeScheduleEdit.setStartTime(employeeSchedule.getStartTime());
            employeeScheduleEdit.setEndTime(employeeSchedule.getEndTime());

            employeeScheduleService.updateEmployeeSchedule(employeeSchedule);
            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleEdit, HttpStatus.OK);
        }

        return response;
    }

//    @PostMapping("/deleteSchedule/{scheduleId}")
//    public ResponseEntity<?> deleteEmployeeSchedule(@Valid @RequestBody EmployeeSchedule employeeSchedule) {
//
//    }

    @GetMapping("/viewEmployeeAvailability")
    public ResponseEntity<?> viewEmployeeAvailability(@Valid @RequestBody Employee employee) {
        Long id = employee.getId();
        List<EmployeeAvailabilityForm> employeeAvailabilityList = employeeScheduleService.getEmployeeAvailability(id);
        
        return new ResponseEntity<List<EmployeeAvailabilityForm>>(employeeAvailabilityList, HttpStatus.OK);
    }

    @GetMapping("/viewServicesWithinTime")
    public ResponseEntity<?> viewServicesWithinTime(@Valid @RequestBody Map<String, String> request) {
        List<?> employeeScheduleTimeList = employeeScheduleService.getServicesWithinParameters(request);
        return new ResponseEntity<List<EmployeeScheduleWithinTimeForm>>((List<EmployeeScheduleWithinTimeForm>) employeeScheduleTimeList, HttpStatus.OK);
    }

    @GetMapping("/viewServicesWithinTimeAndEmployee")
    public ResponseEntity<?> viewServicesWithinTimeAndEmployee(@Valid @RequestBody Map<String, String> request) {
        List<?> employeeScheduleTimeList = employeeScheduleService.getServicesWithinParameters(request);
        return new ResponseEntity<List<EmployeeScheduleWithinTimeFormAndEmployee>>((List<EmployeeScheduleWithinTimeFormAndEmployee>) employeeScheduleTimeList, HttpStatus.OK);
    }
}
