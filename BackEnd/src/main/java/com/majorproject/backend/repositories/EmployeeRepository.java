package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Override
    Iterable<Employee> findAllById(Iterable<Long> iterable);
}
