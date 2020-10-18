package com.majorproject.backend.web;

import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.responseForms.EmpSchByEmpIdForm;
import com.majorproject.backend.responseForms.EmployeeByBServiceIdForm;
import com.majorproject.backend.services.EmployeeScheduleService;
import com.majorproject.backend.responseForms.ListWithTimeboundForm;
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
     * Creates an employee schedule
     * @param request A map of String that contains the employee schedule details
     * @param result Binding result
     * @return The employee schedule created, if successful
     */
    @PostMapping("/create")
    public ResponseEntity<?> createEmployeeSchedule(@Valid @RequestBody Map<String, String> request, BindingResult result) {
        ResponseEntity<?> response;

        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);
        if(errorMap != null) {
            response = errorMap;
        } else {
            EmployeeSchedule employeeScheduleNew = employeeScheduleService.saveOrEditEmployeeSchedule(request, "save", null);
            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleNew, HttpStatus.CREATED);
        }

        return response;
    }

    /**
     * Finds all employee schedule based on time requested by the user (not based on curr time by user)
     * @param dateAPI The date entered
     * @param startTimeAPI The start time entered
     * @param endTimeAPI The end time entered
     * @return A custom employee schedule list based on time requested
     */
    @GetMapping("/getSchedules/date/{dateAPI}/{startTimeAPI}/{endTimeAPI}")
    public ResponseEntity<?> viewSchedulesWithinTime(@Valid @PathVariable String dateAPI,
                                                     @PathVariable String startTimeAPI,
                                                     @PathVariable String endTimeAPI) {
        ListWithTimeboundForm listWithTimeboundForm = employeeScheduleService.getSchedulesWithinTime(dateAPI, startTimeAPI, endTimeAPI);
        return new ResponseEntity<ListWithTimeboundForm>(listWithTimeboundForm, HttpStatus.OK);
    }

    /**
     * Finds the employee schedules based on employeeId
     * @param employeeIdAPI The employeeId
     * @return A custom employee schedule list based on the employeeId
     */
    @GetMapping("/getSchedules/employee/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeId(@Valid @PathVariable String employeeIdAPI) {
        List<EmpSchByEmpIdForm> customScheduleList = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "all");
        return new ResponseEntity<List<EmpSchByEmpIdForm>>(customScheduleList, HttpStatus.OK);
    }

    /**
     * Finds the employee schedules based on employeeId
     * employee schedules must be available (availability = true) and after today's date and time
     * @param employeeIdAPI The employeeId
     * @return A custom employee schedule list based on said above
     */
    @GetMapping("/getSchedules/employee/available/{employeeIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeIdByAvailable(@Valid @PathVariable String employeeIdAPI) {
        List<EmpSchByEmpIdForm> customScheduleList = employeeScheduleService.getSchedulesByEmployeeId(employeeIdAPI, "byAvailable");
        return new ResponseEntity<List<EmpSchByEmpIdForm>>(customScheduleList, HttpStatus.OK);
    }

    /**
     * Finds the employee schedule from now to next week based on employeeId
     * @param employeeIdAPI The employeeId
     * @return A custom employee schedule list based on now to next week
     */
    @GetMapping("/getSchedules/week/{employeeIdAPI}/")
    public ResponseEntity<?> viewSchedulesByEmployeeIdByWeek(@Valid @PathVariable String employeeIdAPI) {
        ListWithTimeboundForm listWithTimeboundForm = employeeScheduleService.getSchedulesByEmployeeIdAndDate(employeeIdAPI);
        return new ResponseEntity<ListWithTimeboundForm>(listWithTimeboundForm, HttpStatus.OK);
    }

    /**
     * Edits the employee schedule
     * @param scheduleIdAPI The employeeScheduleId
     * @param request A map of String that contains the employee schedule edited details
     * @param result Binding result
     * @return The edited schedule, if successful
     */
    @PostMapping("/editSchedule/{scheduleIdAPI}")
    public ResponseEntity<?> editSchedule(@Valid @PathVariable String scheduleIdAPI, @RequestBody Map<String, String> request, BindingResult result) {
        ResponseEntity<?> response;
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationService(result);

        if(errorMap != null) {
            response = errorMap;
        } else {
            EmployeeSchedule employeeScheduleEdit = employeeScheduleService.saveOrEditEmployeeSchedule(request, "edit", scheduleIdAPI);
            response = new ResponseEntity<EmployeeSchedule>(employeeScheduleEdit, HttpStatus.OK);
        }

        return response;
    }

    /**
     * Gets a list of employees from a specific bservice based on the bservice requested by the user
     * @param bServiceIdAPI The bservice id
     * @return A custom list based on above
     */
    @GetMapping("/getEmployees/bService/{bServiceIdAPI}")
    public ResponseEntity<?> viewEmployeesByBServiceIdFromNow(@Valid @PathVariable String bServiceIdAPI) {
        List<EmployeeByBServiceIdForm> employeeList = employeeScheduleService.getSchedulesByBServiceIdAndNow(bServiceIdAPI);
        return new ResponseEntity<List<EmployeeByBServiceIdForm>>(employeeList, HttpStatus.OK);
    }

    /**
     * Gets a list of employee schedules based on the employeeId and serviceId
     * @param employeeIdAPI The employee id
     * @param bServiceIdAPI The bservice id
     * @return A custom list based on the employeeId and serviceId
     */
    @GetMapping("/getSchedules/employee/bService/{employeeIdAPI}/{bServiceIdAPI}")
    public ResponseEntity<?> viewSchedulesByEmployeeAndBService(@Valid @PathVariable String employeeIdAPI,
                                                                @PathVariable String bServiceIdAPI) {
        ListWithTimeboundForm listWithTimeboundForm = employeeScheduleService.getSchedulesByEmployeeAndBService(employeeIdAPI, bServiceIdAPI);
        return new ResponseEntity<ListWithTimeboundForm>(listWithTimeboundForm, HttpStatus.OK);
    }
}
