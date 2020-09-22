package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Employee;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    @Override
    Iterable<Employee> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT e.* FROM Employee e", nativeQuery = true)
    List<Employee> findAllEmployees();

    @Query(value = "SELECT e.* FROM Employee e WHERE e.email = ?1", nativeQuery = true)
    Employee findByEmail(String email);

    @Query(value = "SELECT e.* FROM Employee e WHERE e.username = ?1 AND e.password = ?2", nativeQuery = true)
    Employee findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT e.* FROM Employee e WHERE e.username = ?1", nativeQuery = true)
    Employee findByUsername(String username);

    @Query(value = "SELECT e.* FROM Employee e WHERE e.employee_id = ?1", nativeQuery = true)
    Employee findByEmployeeId(long employeeId);
}
