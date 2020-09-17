package com.majorproject.backend.web;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.services.EmployeeScheduleService;
import com.majorproject.backend.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("api/employeeSchedule")
@CrossOrigin
public class EmployeeScheduleController {

    @Autowired
    private EmployeeScheduleService employeeScheduleService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @PostMapping("")
    public ResponseEntity<?> createEmployee(@Valid @RequestBody Map<String, String> request) {
        EmployeeSchedule employeeScheduleNew = employeeScheduleService.saveOrUpdateEmployee(request);

        return new ResponseEntity<EmployeeSchedule>(employeeScheduleNew, HttpStatus.CREATED);
    }

}
