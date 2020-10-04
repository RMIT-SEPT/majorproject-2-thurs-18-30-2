package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.BService;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.EmployeeScheduleRepository;
import com.majorproject.backend.repositories.BServiceRepository;
import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;
import com.majorproject.backend.responseForms.EmployeeScheduleServicesAndDateForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EmployeeScheduleService {
    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private BServiceRepository bServiceRepository;

    private IdErrorService idErrorService = new IdErrorService();
    private ListEmptyErrorService listEmptyErrorService = new ListEmptyErrorService();
    private ObjectEmptyErrorService objectEmptyErrorService = new ObjectEmptyErrorService();
    private DateErrorService dateErrorService = new DateErrorService();

    /**
     * Creates the employee schedule
     * @param request A map that contains the request details
     * @return The employee schedule
     */
    public EmployeeSchedule saveEmployeeSchedule(Map<String, String> request) {
        EmployeeSchedule employeeSchedule = new EmployeeSchedule();
        EmployeeSchedule employeeScheduleNew = null;

        try {
//            employeeSchedule.setDate(formatterDate.parse(request.get("date")));
//            employeeSchedule.setStartTime(formatterTime.parse(request.get("startTime")));
//            employeeSchedule.setEndTime(formatterTime.parse(request.get("endTime")));
            employeeSchedule.setDate(dateErrorService.convertingDateType(request.get("date"), "date"));
            employeeSchedule.setStartTime(dateErrorService.convertingDateType(request.get("startTime"), "startTime"));
            employeeSchedule.setEndTime(dateErrorService.convertingDateType(request.get("endTime"), "endTime"));
            employeeSchedule.setEmployee(employeeRepository.findByEmployeeId(Long.parseLong(request.get("employeeId"))));
            employeeSchedule.setBService(bServiceRepository.getBServiceById(Long.parseLong(request.get("bServiceId"))));
            employeeSchedule.setAvailability(true);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Scheduling error");
        }

        boolean foundDuplicates = duplicatedSchedule(employeeSchedule);
        if(foundDuplicates) { // there is a duplicate
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
        } else { // No duplicate
            employeeScheduleNew = employeeScheduleRepository.save(employeeSchedule);
        }

        return employeeScheduleNew;
    }

    /**
     * Find any duplicated employee schedule in the database
     * If there are duplicates, return true
     * If there are no duplicates, return false
     * @param employeeSchedule The employee's schedule
     * @return A boolean that determine if there is any duplicates
     */
    public boolean duplicatedSchedule(EmployeeSchedule employeeSchedule) {
        boolean found = false;
        List<EmployeeSchedule> employeeScheduleList = employeeScheduleRepository.getAllEmployeeSchedules();

        // Checks employee, bService, date, startTime, endTime
        for(int i = 0; i < employeeScheduleList.size() && !found; ++i) {
//            if(employeeSchedule.getEmployee().equals(employeeScheduleList.get(i).getEmployee()) &&
//                    employeeSchedule.getBService().equals(employeeScheduleList.get(i).getBService()) &&
//                    employeeSchedule.getDate().equals(employeeScheduleList.get(i).getDate()) &&
//                    employeeSchedule.getStartTime().equals(employeeScheduleList.get(i).getStartTime()) &&
//                    employeeSchedule.getEndTime().equals(employeeScheduleList.get(i).getEndTime())) {
//                found = true;
//            }
            if(employeeSchedule.getEmployee().equals(employeeScheduleList.get(i).getEmployee()) &&
            employeeSchedule.getDate().equals(employeeScheduleList.get(i).getDate())) {
                Date scheduleStartTime = employeeSchedule.getStartTime();
                Date scheduleEndTime = employeeSchedule.getEndTime();

                Date listStartTime = employeeScheduleList.get(i).getStartTime();
                Date listEndTime = employeeScheduleList.get(i).getEndTime();

                if((scheduleStartTime.before(listStartTime) && scheduleEndTime.before(listStartTime)) ||
                        (scheduleStartTime.after(listEndTime) && scheduleEndTime.after(listEndTime))) {

//                if((scheduleStartTime.after(listStartTime) && scheduleEndTime.before(listEndTime)) ||
//                        (scheduleStartTime.equals(listStartTime) && scheduleEndTime.equals(listEndTime)) ||
//                        (scheduleStartTime.after(listStartTime) && scheduleStartTime.before(listEndTime)) ||
//                        (scheduleEndTime.before(listEndTime) && scheduleEndTime.after(listStartTime))) {
                    found = false;
                } else {
                    found = true;
                }
            }
        }

        return found;
    }

    // Check for employee schedules, by date, start time and end time
//    public List<EmployeeScheduleAvailabilityForm> getSchedulesWithinTime(String dateAPI,
//                                                                         String startTimeAPI,
//                                                                         String endTimeAPI) {
    public ListWithTimeboundService getSchedulesWithinTime(String dateAPI,
                                                                         String startTimeAPI,
                                                                         String endTimeAPI) {
        List<EmployeeSchedule> employeeScheduleList;
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        Date date = dateErrorService.convertingDateType(dateAPI, "date");
        Date startTime = dateErrorService.convertingDateType(startTimeAPI, "time");
        Date endTime = dateErrorService.convertingDateType(endTimeAPI, "time");

        employeeScheduleList = employeeScheduleRepository.findSchedulesByDateAndTime(date, startTime, endTime);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;
//        return employeeScheduleAvailabilityList;
    }

    // Check for employee schedules, by employeeId
    public ListWithTimeboundService getSchedulesByEmployeeId(String employeeIdAPI, String repoCallType) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long employeeId = idErrorService.idStringToLong(employeeIdAPI);

        // Checking which repo call it would be
        if(repoCallType.equals("all")) {
            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeId(employeeId);
        } else if(repoCallType.equals("byAvailable")) {
            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdAvailability(employeeId);
        }

        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;

//        return employeeScheduleAvailabilityList;
    }

    // Check for employee schedules, by employeeId and date
    public ListWithTimeboundService getSchedulesByEmployeeIdAndDate(String employeeIdAPI, String dateAPI, String weekAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long employeeId = idErrorService.idStringToLong(employeeIdAPI);
        Date date = dateErrorService.convertingDateType(dateAPI, "date");
        Date week = dateErrorService.convertingDateType(weekAPI, "date");

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdDate(employeeId, date, week);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;

//        return employeeScheduleAvailabilityList;
    }

    /**
     * Returns the employee schedule based on the schedule's Id
     * @param scheduleId The schedule's Id
     * @return The employee schedule
     */
    public EmployeeSchedule getEmployeeScheduleByScheduleId(Long scheduleId) {
        EmployeeSchedule employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);
        objectEmptyErrorService.checkIfNull(employeeSchedule, "Employee Schedule does not exist");

        return employeeSchedule;
    }

    public ListWithTimeboundService getScheduleByBServiceId(String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByBServiceId(bServiceId);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundService listWithTimeboundService = new ListWithTimeboundService(employeeScheduleAvailabilityList);
        return listWithTimeboundService;

//        return employeeScheduleAvailabilityList;
    }

    public List<EmployeeScheduleAvailabilityForm> convertToAvailabilityList(List<EmployeeSchedule> listOld,
                                                                            List<EmployeeScheduleAvailabilityForm> listNew) {
        for(int i = 0; i < listOld.size(); ++i) {
            listNew.add(new EmployeeScheduleAvailabilityForm(listOld.get(i)));
        }

        return listNew;
    }

    public List<EmployeeScheduleServicesAndDateForm> getSchedulesByBServiceIdAndNow(String bServiceIdAPI, String dateAPI, String currTimeAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleServicesAndDateForm> employeeScheduleServicesAndDateFormList = new ArrayList<EmployeeScheduleServicesAndDateForm>();

        long bServiceId = idErrorService.idStringToLong(bServiceIdAPI);
        Date date = dateErrorService.convertingDateType(dateAPI, "date");
        Date currTime = dateErrorService.convertingDateType(currTimeAPI, "time");

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByBServiceIdAndNow(bServiceId, date, currTime);
        listEmptyErrorService.checkListEmpty(employeeScheduleList, "Employee Schedule");

        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            employeeScheduleServicesAndDateFormList.add(new EmployeeScheduleServicesAndDateForm(employeeScheduleList.get(i)));
        }

        return employeeScheduleServicesAndDateFormList;
    }

    public EmployeeSchedule editSchedule(String scheduleIdAPI, Map<String, String> request) {
        Long scheduleId = idErrorService.idStringToLong(scheduleIdAPI);
        EmployeeSchedule employeeScheduleEdit = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);

        // Seting employee details
        Long employeeId = idErrorService.idStringToLong(request.get("employeeId"));
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        employeeScheduleEdit.setEmployee(employee);

        Long bServiceId = idErrorService.idStringToLong(request.get("bServiceId"));
        BService bService = bServiceRepository.getBServiceById(bServiceId);
        employeeScheduleEdit.setBService(bService);

        Date date = dateErrorService.convertingDateType(request.get("date"), "date");
        employeeScheduleEdit.setDate(date);

        Date startTime = dateErrorService.convertingDateType(request.get("startTime"), "time");
        employeeScheduleEdit.setStartTime(startTime);

        Date endTime = dateErrorService.convertingDateType(request.get("endTime"), "time");
        employeeScheduleEdit.setEndTime(endTime);

        boolean availability = Boolean.parseBoolean(request.get("availability"));
        employeeScheduleEdit.setAvailability(availability);

//        EmployeeSchedule emmployeeScheduleCurr = employeeScheduleRepository.getDuplicateSchedules(employeeId, bServiceId, date, startTime, endTime, availability);
//        if(emmployeeScheduleCurr == null) {
//            employeeScheduleEdit.setEmployee(employee);
//            employeeScheduleEdit.setBService(bService);
//            employeeScheduleEdit.setDate(date);
//            employeeScheduleEdit.setStartTime(startTime);
//            employeeScheduleEdit.setEndTime(endTime);
//            employeeScheduleEdit.setAvailability(availability);
//            employeeScheduleRepository.save(employeeScheduleEdit);
//        }

        employeeScheduleRepository.save(employeeScheduleEdit);

        return employeeScheduleEdit;
    }

//    /**
//     * Returns a custom list(see form class) that contains the employee's availability based on the employee's id
//     * and week if byWeek = true
//     * @param employeeId The employee's Id
//     * @param byWeek A boolean that checks if the list should only contain dates from today till a week
//     * @return A custom list that contains the employee's availability
//     */
//    public List<EmployeeScheduleAvailabilityForm> getEmployeeAvailability(Long employeeId, boolean byWeek) {
//        List<EmployeeSchedule> employeeScheduleList;
//        if(byWeek) { // Get only today till next week
//            try {
//                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//                LocalDateTime currentDate = LocalDateTime.now();
//                Date now = formatterDate.parse(currentDate.format(dtf));
//                LocalDateTime currentWeekFromDate = currentDate.plusWeeks(1);
//                Date nextWeekDate = formatterDate.parse(currentWeekFromDate.format(dtf));
//                employeeScheduleList = employeeScheduleRepository.getEmployeeAvailabilityByIdInWeek(employeeId, now, nextWeekDate);
//            } catch(Exception e) {
//                throw new ResponseException(HttpStatus.BAD_REQUEST, "Date Error");
//            }
//
//        } else { // Get all employee availability
//            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeId(employeeId);
//        }
//
//        if(employeeScheduleList.isEmpty()) {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "No time available");
//        }
//
//        List<EmployeeScheduleAvailabilityForm> employeeAvailability = new ArrayList<EmployeeScheduleAvailabilityForm>();
//
//        for(int i = 0; i < employeeScheduleList.size(); ++i) {
//            employeeAvailability.add(new EmployeeScheduleAvailabilityForm(employeeScheduleList.get(i)));
//        }
//
//        return employeeAvailability;
//    }

    /*** Future code ***/

////    public EmployeeSchedule editEmployeeSchedule(EmployeeSchedule employeeSchedule) {
//    public EmployeeSchedule editEmployeeSchedule(String id, Map <String, String> request) {
//        Long scheduleId = Long.parseLong(id);
//        EmployeeSchedule employeeScheduleEdit = getEmployeeScheduleById(scheduleId);
//
//        try {
//            employeeScheduleEdit.setEmployee(employeeRepository.findByEmployeeId(Long.parseLong(request.get("employeeId"))));
//            employeeScheduleEdit.setBService(bServiceRepository.findByBServiceId(Long.parseLong(request.get("bServiceId"))));
//            employeeScheduleEdit.setDate(formatterDate.parse(request.get("date")));
//            employeeScheduleEdit.setStartTime(formatterTime.parse(request.get("startTime")));
//            employeeScheduleEdit.setEndTime(formatterTime.parse(request.get("endTime")));
//        } catch(Exception e) {
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Scheduling error");
//        }
//
//        boolean foundDuplicates = findAnyDuplicates(employeeScheduleEdit);
//        if(foundDuplicates) { // there is a duplicate
//            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
//        } else { // No duplicate
//            employeeScheduleEdit = employeeScheduleRepository.save(employeeScheduleEdit);
//        }
//
//        return employeeScheduleEdit;
//    }
}
