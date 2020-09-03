package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

    @Override
    Iterable<Employee> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT * FROM EMPLOYEE c WHERE c.email = ?1", nativeQuery = true)
    Employee findByEmail(String email);

    @Query(value = "SELECT * FROM CUSTOMER c WHERE c.username = ?1", nativeQuery = true)
    Employee findByUsername(String username);
}
