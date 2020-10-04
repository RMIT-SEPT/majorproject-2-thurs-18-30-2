package com.majorproject.backend.web;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;
import com.majorproject.backend.responseForms.EmployeeScheduleServicesAndDateForm;
import com.majorproject.backend.services.EmployeeScheduleService;
import com.majorproject.backend.services.ListWithTimeboundService;
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
//            EmployeeSchedule employeeScheduleNew = employeeScheduleService.saveOrUpdateEmployeeSchedule(employeeSchedule);
            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleNew, HttpStatus.CREATED);
        }

        return response;
    }

    // Finds all employee schedule based on time requested by user
    // (Method done)
    @GetMapping("/getSchedules/time/{dateAPI}/{startTimeAPI}/{endTimeAPI}")
    public ResponseEntity<?> viewSchedulesWithinTime(@Valid @PathVariable String dateAPI,
                                                     @PathVariable String startTimeAPI,
                                                     @PathVariable String endTimeAPI) {
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleList = employeeScheduleService.getSchedulesWithinTime(dateAPI, startTimeAPI, endTimeAPI);
//        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleList, HttpStatus.OK);
        ListWithTimeboundService listWithTimeboundService = employeeScheduleService.getSchedulesWithinTime(dateAPI, startTimeAPI, endTimeAPI);
        return new ResponseEntity<ListWithTimeboundService>(listWithTimeboundService, HttpStatus.OK);
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
    @GetMapping("/getSchedules/employee/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeId(@Valid @PathVariable String employeeIdAPI) {
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "all");
//        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
        ListWithTimeboundService listWithTimeboundService = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "all");
        return new ResponseEntity<ListWithTimeboundService>(listWithTimeboundService, HttpStatus.OK);
    }

    // New method for /viewEmployeeAvailability, except it shows only available schedules (availability = true) based on employee Id
    // (Method done)
    @GetMapping("/getSchedules/employee/available/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeIdByAvailable(@Valid @PathVariable String employeeIdAPI) {
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "byAvailable");
//        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
        ListWithTimeboundService listWithTimeboundService = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "byAvailable");
        return new ResponseEntity<ListWithTimeboundService>(listWithTimeboundService, HttpStatus.OK);
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
    @GetMapping("/getSchedules/week/{employeeIdAPI}/{dateAPI}/{weekAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeIdByWeek(@Valid @PathVariable String employeeIdAPI,
                                                             @PathVariable String dateAPI, @PathVariable String weekAPI) {
//        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = employeeScheduleService.getSchedulesByEmployeeIdAndDate(employeeIdAPI, dateAPI);
//        return new ResponseEntity<List<EmployeeScheduleAvailabilityForm>>(employeeScheduleAvailabilityList, HttpStatus.OK);
        ListWithTimeboundService listWithTimeboundService = employeeScheduleService.getSchedulesByEmployeeIdAndDate(employeeIdAPI, dateAPI, weekAPI);
        return new ResponseEntity<ListWithTimeboundService>(listWithTimeboundService, HttpStatus.OK);
    }

    @GetMapping("/getBServices/{bServiceIdAPI}/{dateAPI}/{timeAPI}")
    public ResponseEntity<?> viewBServicesFromToday(@Valid @PathVariable String bServiceIdAPI,
                                                    @PathVariable String dateAPI, @PathVariable String timeAPI) {
        List<EmployeeScheduleServicesAndDateForm> employeeScheduleServicesAndDateFormList = employeeScheduleService.getSchedulesByBServiceIdAndNow(bServiceIdAPI, dateAPI, timeAPI);
        return new ResponseEntity<List<EmployeeScheduleServicesAndDateForm>>(employeeScheduleServicesAndDateFormList, HttpStatus.OK);
    }

    @PutMapping("/editSchedule/{scheduleIdAPI}")
    public ResponseEntity<?> editSchedule(@Valid @PathVariable String scheduleIdAPI, @RequestBody Map<String, String> request, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if(errorMap != null) {
            response = errorMap;
        } else {
            EmployeeSchedule employeeScheduleEdit = employeeScheduleService.editSchedule(scheduleIdAPI, request);
            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleEdit, HttpStatus.OK);
        }

        return response;
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
