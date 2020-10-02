package com.majorproject.backend.web;

import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;
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

    /**
     * Creates the employee schedule
     * @param request A map that contains the details of the request
     * @param result BindingResult
     * @return A response entity of the employee schedule
     */
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
    }

    // Finds all employee schedule based on time requested by user
    // (Method done)
    @GetMapping("/viewSchedulesWithinTime/{dateAPI}/{startTimeAPI}/{endTimeAPI}")
    public ResponseEntity<?> viewSchedulesWithinTime(@Valid @PathVariable String dateAPI,
                                                     @PathVariable String startTimeAPI,
                                                     @PathVariable String endTimeAPI) {
        List<EmployeeScheduleAvailabilityForm> employeeScheduleList = employeeScheduleService.getSchedulesWithinTime(dateAPI, startTimeAPI, endTimeAPI);
        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleList, HttpStatus.OK);
    }

//    // This method is to find all employee schedules based on an employee {id}
//    /**
//     * Finds the availability of a specified employee
//     * @param employee The employee selected
//     * @return A response entity of the list of date and time when the employee is available
//     */
//    @GetMapping("/viewEmployeeAvailability") // Old API call
//    public ResponseEntity<?> viewEmployeeAvailability(@Valid @RequestBody Employee employee) {
//        Long id = employee.getId();
//        boolean byWeek = false;
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getEmployeeAvailability(id, byWeek);
//
//        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
//    }

    // New method for /viewEmployeeAvailability, except it shows all schedules (availability = true and false) based on employee Id
    // (Method done)
    @GetMapping("/viewSchedules/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeId(@Valid @PathVariable String employeeIdAPI) {
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "all");
        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
    }

    // New method for /viewEmployeeAvailability, except it shows only available schedules (availability = true) based on employee Id
    // (Method done)
    @GetMapping("/viewSchedules/available/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeIdByAvailable(@Valid @PathVariable String employeeIdAPI) {
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "byAvailable");
        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
    }

//    // This method is to find all employee schedules from today to next week based on employee {id}
//    /**
//     * Finds the availability of a specified employee from today till a week later (7 days)
//     * @param employee The employee selected
//     * @return A response entity of the list of date and time when the employee is available
//     */
//    @GetMapping("/viewEmployeeAvailabilityWeek")
//    public ResponseEntity<?> viewEmployeeAvailabilityWeek(@Valid @RequestBody Employee employee) {
//        Long id = employee.getId();
//        boolean byWeek = true;
//        List<EmployeeScheduleAvailabilityForm> employeeAvailabilityList = employeeScheduleService.getEmployeeAvailability(id, byWeek);
//
//        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeAvailabilityList, HttpStatus.OK);
//    }

    // New method for /viewEmployeeAvailabilityWeek
    // Only takes in employeeId and the Date (from front end)
    // (Method done)
    @GetMapping("/viewSchedules/week/{dateAPI}/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeIdByWeek(@Valid @PathVariable String dateAPI,
                                                             @PathVariable String employeeIdAPI) {
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getSchedulesByEmployeeIdAndDate(employeeIdAPI, dateAPI);
        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
    }

    /*** Future code ***/

//    @PostMapping("/editSchedule/{id}")
////    public ResponseEntity<?> editEmployeeSchedule(@Valid @PathVariable String id, @RequestBody EmployeeSchedule employeeSchedule, BindingResult result) {
//    public ResponseEntity<?> editEmployeeSchedule(@Valid @PathVariable String id,
//                                                  @RequestBody Map<String, String> request, BindingResult result) {
//        ResponseEntity<?> response;
//
//        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
//        Long scheduleId = Long.parseLong(id);
//
//        if(errorMap != null) {
//            response = errorMap;
//        } else {
////            EmployeeSchedule employeeScheduleEdit = employeeScheduleService.editEmployeeSchedule(id, request);
//            EmployeeSchedule employeeScheduleEdit = employeeScheduleService.saveEmployeeSchedule(request);
//            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleEdit, HttpStatus.OK);
//        }
//
//        return response;
//    }
//
//    @PostMapping("/deleteSchedule/{scheduleId}")
//    public ResponseEntity<?> deleteEmployeeSchedule(@Valid @RequestBody EmployeeSchedule employeeSchedule) {
//
//    }
}
