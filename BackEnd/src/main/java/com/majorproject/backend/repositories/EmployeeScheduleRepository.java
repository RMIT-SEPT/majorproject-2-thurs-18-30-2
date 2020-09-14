package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.EmployeeSchedule;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeScheduleRepository extends CrudRepository<EmployeeSchedule, Long> {
    @Override
    Iterable<EmployeeSchedule> findAllById(Iterable<Long> iterable);
}
