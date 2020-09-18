package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeScheduleRepository extends CrudRepository<EmployeeSchedule, Long> {
    @Override
    Iterable<EmployeeSchedule> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT es.date, es.startTime, es.endTime" +
            "FROM Employee_Schedule es, Employee e" +
            "WHERE es.employee_id = e.employee_id AND" +
            "e.username = ?1" +
            "ORDER BY es.date", nativeQuery = true)
    List<EmployeeSchedule> getEmployeeAvailabilityByUsername(String username);
}
