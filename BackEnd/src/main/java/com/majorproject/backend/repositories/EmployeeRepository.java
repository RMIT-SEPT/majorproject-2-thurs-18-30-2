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

    @Override
    List<Employee> findAll();

    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.email = ?1", nativeQuery = true)
    Employee findByEmail(String email);

    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.username = ?1 AND e.password = ?2", nativeQuery = true)
    Employee findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM EMPLOYEE e WHERE e.username = ?1", nativeQuery = true)
    Employee findByUsername(String username);

    @Query(value = "UPDATE EMPLOYEE SET e.?2 = ?3, where e.username = ?1", nativeQuery = true)
    void editEmployeeDetail(String username, String column, String editDetail);
}
