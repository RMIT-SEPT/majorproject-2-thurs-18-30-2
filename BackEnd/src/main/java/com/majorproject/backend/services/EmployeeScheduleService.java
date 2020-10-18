package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.BService;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import com.majorproject.backend.repositories.EmployeeRepository;
import com.majorproject.backend.repositories.EmployeeScheduleRepository;
import com.majorproject.backend.repositories.BServiceRepository;
import com.majorproject.backend.responseForms.EmpSchByEmpIdForm;
import com.majorproject.backend.responseForms.EmployeeByBServiceIdForm;
import com.majorproject.backend.responseForms.EmployeeScheduleAvailabilityForm;
import com.majorproject.backend.responseForms.ListWithTimeboundForm;
import com.majorproject.backend.util.*;
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

    private IdErrorUtil idErrorUtil = new IdErrorUtil();
    private ListEmptyErrorUtil listEmptyErrorUtil = new ListEmptyErrorUtil();
    private ObjectEmptyErrorUtil objectEmptyErrorUtil = new ObjectEmptyErrorUtil();
    private DateErrorUtil dateErrorUtil = new DateErrorUtil();
    private DateNowUtil dateNowUtil = new DateNowUtil();

    /**
     * Saves or edit/update the employee schedule
     * @param request The map that contains the employee schedule details
     * @param storingType A string that states if its a save or edit of employee schedule
     * @param scheduleIdAPI The employeeScheduleId (null if its storingType = save)
     * @return The employee schedule if successfully created
     */
    public EmployeeSchedule saveOrEditEmployeeSchedule(Map<String, String> request, String storingType, String scheduleIdAPI) {
        EmployeeSchedule employeeSchedule;
        String employeeIdString = request.get("employeeId");
        String bServiceIdString = request.get("bServiceId");
        String dateString = request.get("date");
        String startTimeString = request.get("startTime");
        String endTimeString = request.get("endTime");

        long duplicatedScheduleId = checkForDuplicates(employeeIdString, bServiceIdString, dateString, startTimeString, endTimeString);
        if(storingType.equals("save") && duplicatedScheduleId == -1) { // new employeeSchedule, no duplicates
            employeeSchedule = new EmployeeSchedule();

            // Set availability
            employeeSchedule.setAvailability(true); // set to true since its new schedule

        } else if(storingType.equals("edit")) {
            Long scheduleId = idErrorUtil.idStringToLong(scheduleIdAPI);

            /**
             * edit scheduleId = 3, details are all there
             *
             * if list id = 4, schedule duplicated
             * if list id = 3, correct schedule, edit it
             * if list id = -1, correct schedule, edit it
             */
            if(duplicatedScheduleId == scheduleId || duplicatedScheduleId == -1) { // no duplicates, editing the found one
                employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);

                // Set availability
                boolean availability = Boolean.parseBoolean(request.get("availability"));
                employeeSchedule.setAvailability(availability);
            } else { // duplicated, throw exception
                throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
            }
        } else { // duplicated, throw exception
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Duplicated schedule");
        }

        // Setting details
        Long employeeId = idErrorUtil.idStringToLong(employeeIdString);
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        employeeSchedule.setEmployee(employee);

        Long bServiceId = idErrorUtil.idStringToLong(bServiceIdString);
        BService bService = bServiceRepository.getBServiceById(bServiceId);
        employeeSchedule.setBService(bService);

        Date date = dateErrorUtil.convertToDateType(dateString, "date");
        employeeSchedule.setDate(date);

        Date startTime = dateErrorUtil.convertToDateType(startTimeString, "time");
        employeeSchedule.setStartTime(startTime);

        Date endTime = dateErrorUtil.convertToDateType(endTimeString, "time");
        employeeSchedule.setEndTime(endTime);

        employeeScheduleRepository.save(employeeSchedule);

        return employeeSchedule;
    }

    /**
     * This method is used to find if there is already a schedule inside
     * A duplicated schedule is found if the employee has a schedule clashing with the one being created
     * Checks are based on employeeId and date and time
     * @param employeeIdAPI The employee id
     * @param bServiceIdAPI The BService id
     * @param dateAPI The schedule's date
     * @param startTimeAPI The schedule's start time
     * @param endTimeAPI The schedule's end time
     * @return The employee schedule id, or -1 if no employee schedule found
     */
    public long checkForDuplicates(String employeeIdAPI, String bServiceIdAPI, String dateAPI, String startTimeAPI, String endTimeAPI) {
//        boolean found = false;
        EmployeeSchedule employeeSchedule;
        long duplicatedScheduleId = -1;

        long employeeId = idErrorUtil.idStringToLong(employeeIdAPI);
        long bServiceId = idErrorUtil.idStringToLong(bServiceIdAPI);
        Date date = dateErrorUtil.convertToDateType(dateAPI, "date");
        Date startTime = dateErrorUtil.convertToDateType(startTimeAPI, "time");
        Date endTime = dateErrorUtil.convertToDateType(endTimeAPI, "time");

        // Check if employee and bService exist
        Employee employee = employeeRepository.findByEmployeeId(employeeId);
        BService bService = bServiceRepository.getBServiceById(bServiceId);
        if(employee == null || bService == null) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Employee or BService error");
        }

        try {
            employeeSchedule = employeeScheduleRepository.getDuplicatedSchedules(employeeId, date, startTime, endTime);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.BAD_REQUEST, "Schedule error");
        }

        if(employeeSchedule != null) {
            duplicatedScheduleId = employeeSchedule.getId();
        }
        return duplicatedScheduleId;
    }

    /**
     * Gets employee schedules based on date, start time and end time
     * @param dateAPI The date
     * @param startTimeAPI The start time
     * @param endTimeAPI The end time
     * @return A custom list based on above
     */
    public ListWithTimeboundForm getSchedulesWithinTime(String dateAPI,
                                                        String startTimeAPI,
                                                        String endTimeAPI) {
        List<EmployeeSchedule> employeeScheduleList;
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        Date date = dateErrorUtil.convertToDateType(dateAPI, "date");
        Date startTime = dateErrorUtil.convertToDateType(startTimeAPI, "time");
        Date endTime = dateErrorUtil.convertToDateType(endTimeAPI, "time");

        employeeScheduleList = employeeScheduleRepository.findSchedulesByDateAndTime(date, startTime, endTime);
        listEmptyErrorUtil.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundForm listWithTimeboundForm = new ListWithTimeboundForm(employeeScheduleAvailabilityList);

        return listWithTimeboundForm;
    }

    /**
     * Checks for employee schedules, based on employee id
     * @param employeeIdAPI The employee id
     * @param repoCallType Checking for either all or by availability and date now
     * @return A list of employee schedules based on above
     */
    public List<EmpSchByEmpIdForm> getSchedulesByEmployeeId(String employeeIdAPI, String repoCallType) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmpSchByEmpIdForm> customScheduleList = new ArrayList<EmpSchByEmpIdForm>();

        long employeeId = idErrorUtil.idStringToLong(employeeIdAPI);

        // Checking which repo call it would be
        if(repoCallType.equals("all")) {
            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeId(employeeId);
        } else if(repoCallType.equals("byAvailable")) {
            Date currDate = dateNowUtil.getCurrentDate();
            Date currTime = dateNowUtil.getCurrentTime();
            employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdAvailability(employeeId, currDate, currTime);
        }

        listEmptyErrorUtil.checkListEmpty(employeeScheduleList, "Employee Schedule");

        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            customScheduleList.add(new EmpSchByEmpIdForm(employeeScheduleList.get(i)));
        }

        return customScheduleList;
    }

    /**
     * Gets a list of employee schedules, based on employee id and date
     * @param employeeIdAPI The employee id
     * @return A custom list of employee schedules based on employee id and date
     */
    public ListWithTimeboundForm getSchedulesByEmployeeIdAndDate(String employeeIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long employeeId = idErrorUtil.idStringToLong(employeeIdAPI);
        Date date = dateNowUtil.getCurrentDate();
        Date week = dateNowUtil.getCurrentWeek();

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdDate(employeeId, date, week);
        listEmptyErrorUtil.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundForm listWithTimeboundForm = new ListWithTimeboundForm(employeeScheduleAvailabilityList);
        return listWithTimeboundForm;
    }

    /**
     * Returns the employee schedule based on the schedule's Id
     * @param scheduleId The schedule's Id
     * @return The employee schedule
     */
    public EmployeeSchedule getEmployeeScheduleByScheduleId(Long scheduleId) {
        EmployeeSchedule employeeSchedule = employeeScheduleRepository.getEmployeeScheduleById(scheduleId);
        objectEmptyErrorUtil.checkIfNull(employeeSchedule, "Employee Schedule does not exist");

        return employeeSchedule;
    }

    public List<EmployeeByBServiceIdForm> getEmployeesByBServiceId(String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeByBServiceIdForm> employeeList = new ArrayList<EmployeeByBServiceIdForm>();

        long bServiceId = idErrorUtil.idStringToLong(bServiceIdAPI);

        employeeScheduleList = employeeScheduleRepository.getEmployeeByBServiceIdOnlyAvailable(bServiceId);
        listEmptyErrorUtil.checkListEmpty(employeeScheduleList, "Employee");

        List<Long> storeEmployeeId = new ArrayList<Long>();
        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            // Check for duplicated employeeId
            boolean duplicated = false;
            long listEmployeeId = employeeScheduleList.get(i).getEmployee().getId();

            for(int j = 0; j < storeEmployeeId.size() && !duplicated; ++j) {
                if(listEmployeeId == storeEmployeeId.get(j)) {
                    duplicated = true;
                }
            }

            if(!duplicated) {
                employeeList.add(new EmployeeByBServiceIdForm(employeeScheduleList.get(i)));
                storeEmployeeId.add(listEmployeeId);
            }
        }

        return employeeList;
    }

    /**
     * Gets the employee schedules based on BService id and date and time
     * @param bServiceIdAPI The BService id
     * @return A custom list of employee schedules based on above
     */
    public List<EmployeeByBServiceIdForm> getSchedulesByBServiceIdAndNow(String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeByBServiceIdForm> employeeList = new ArrayList<EmployeeByBServiceIdForm>();

        long bServiceId = idErrorUtil.idStringToLong(bServiceIdAPI);
        Date currDate = dateNowUtil.getCurrentDate();
        Date currTime = dateNowUtil.getCurrentTime();

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByBServiceIdAndNowOnlyAvailable(bServiceId, currDate, currTime);
        listEmptyErrorUtil.checkListEmpty(employeeScheduleList, "Employee");

        for(int i = 0; i < employeeScheduleList.size(); ++i) {
            employeeList.add(new EmployeeByBServiceIdForm(employeeScheduleList.get(i)));
        }

        return employeeList;
    }

    /**
     * Gets a list of employee schedules based on the employee id and BService id
     * @param employeeIdAPI The employee id
     * @param bServiceIdAPI The BService id
     * @return A custom list of employee schedules based on above
     */
    public ListWithTimeboundForm getSchedulesByEmployeeAndBService(String employeeIdAPI, String bServiceIdAPI) {
        List<EmployeeSchedule> employeeScheduleList = new ArrayList<EmployeeSchedule>();
        List<EmployeeScheduleAvailabilityForm> employeeScheduleAvailabilityList = new ArrayList<EmployeeScheduleAvailabilityForm>();

        long employeeId = idErrorUtil.idStringToLong(employeeIdAPI);
        long bServiceId = idErrorUtil.idStringToLong(bServiceIdAPI);
        Date currDate = dateNowUtil.getCurrentDate();
        Date currTime = dateNowUtil.getCurrentTime();

        employeeScheduleList = employeeScheduleRepository.getEmployeeScheduleByEmployeeIdAndBServiceIdAndNow(employeeId, bServiceId,
                                                                                                        currDate, currTime);
        listEmptyErrorUtil.checkListEmpty(employeeScheduleList, "Employee Schedule");

        convertToAvailabilityList(employeeScheduleList, employeeScheduleAvailabilityList);
        ListWithTimeboundForm listWithTimeboundForm = new ListWithTimeboundForm(employeeScheduleAvailabilityList);
        return listWithTimeboundForm;
    }

    /**
     * Creates a custom list from another list
     * This method is used in this class, to avoid code duplication
     * @param listOld The main list
     * @param listNew The custom list
     * @return The custom list
     */
    public List<EmployeeScheduleAvailabilityForm> convertToAvailabilityList(List<EmployeeSchedule> listOld,
                                                                            List<EmployeeScheduleAvailabilityForm> listNew) {
        for(int i = 0; i < listOld.size(); ++i) {
            listNew.add(new EmployeeScheduleAvailabilityForm(listOld.get(i)));
        }

        return listNew;
    }
}
