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

    /**
     * This query returns a list of all rows in the Employee database
     * @return The list based on ^
     */
    @Query(value = "SELECT e.* FROM Employee e", nativeQuery = true)
    List<Employee> findAllEmployees();

    /**
     * This query returns an employee based on the email entered by the user
     * @param email The email entered by the user
     * @return An employee if the employee exists, otherwise null
     */
    @Query(value = "SELECT e.* FROM Employee e WHERE e.email = ?1", nativeQuery = true)
    Employee findByEmail(String email);

    /**
     * This query returns an employee based on the username and password entered by the user
     * It is used to check for credential validation
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return An employee if the employee exists, otherwise null
     */
    @Query(value = "SELECT e.* FROM Employee e WHERE e.username = ?1 AND e.password = ?2", nativeQuery = true)
    Employee findByUsernameAndPassword(String username, String password);

    /**
     * This query returns an employee based on the username
     * @param username The username of the employee
     * @return An employee if the employee exists, otherwise null
     */
    @Query(value = "SELECT e.* FROM Employee e WHERE e.username = ?1", nativeQuery = true)
    Employee findByUsername(String username);

    /**
     * This query returns an employee based on the employee's id
     * @param employeeId The id of the employee
     * @return An employee if the employee exists, otherwise null
     */
    @Query(value = "SELECT e.* FROM Employee e WHERE e.id = ?1", nativeQuery = true)
    Employee findByEmployeeId(long employeeId);
}
