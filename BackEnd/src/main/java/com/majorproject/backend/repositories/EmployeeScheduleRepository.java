package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface EmployeeScheduleRepository extends CrudRepository<EmployeeSchedule, Long> {
    @Override
    Iterable<EmployeeSchedule> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT es.* FROM Employee_Schedule es", nativeQuery = true)
    List<EmployeeSchedule> getAllEmployeeSchedules();

    @Query(value = "SELECT es.* FROM Employee_Schedule es WHERE es.employee_schedule_id = ?1", nativeQuery = true)
    EmployeeSchedule getEmployeeScheduleById(Long scheduleId);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.availability = true AND " +
            "e.employee_id = ?1 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeAvailabilityById(Long employeeId);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.availability = true AND " +
            "e.employee_id = ?1 AND " +
            "es.date >= ?2 AND " +
            "es.date <= ?3 " +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeAvailabilityByIdInWeek(Long employeeId, Date now, Date nextWeekDate);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e, BService bs " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.service_id = bs.bservice_id AND " +
            "es.availability = true AND " +
            "bs.bservice_id = ?1 AND " +
            "es.date = ?2 AND " +
            "es.start_time >= ?3 AND " +
            "es.end_time <= ?4", nativeQuery = true)
    List<EmployeeSchedule> findSchedulesWithinParameters(Long bServiceId, Date date, Date startTime, Date endTime);

    @Query(value = "SELECT es.* " +
            "FROM Employee_Schedule es, Employee e, BService bs " +
            "WHERE es.employee_id = e.employee_id AND " +
            "es.service_id = bs.bservice_id AND " +
            "es.availability = true AND " +
            "bs.bservice_id = ?1 AND " +
            "e.employee_id = ?2 AND " +
            "es.date = ?3 AND " +
            "es.start_time >= ?4 AND " +
            "es.end_time <= ?5", nativeQuery = true)
    List<EmployeeSchedule> findSchedulesWithinParameters(Long bServiceId, Long employeeId, Date date, Date startTime, Date endTime);
}
