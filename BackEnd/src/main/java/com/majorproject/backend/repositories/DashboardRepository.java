package com.majorproject.backend.repositories;

import com.majorproject.backend.dashboard.Dashboard;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DashboardRepository extends CrudRepository<Dashboard, Long> {
    @Query(value = "SELECT TOP 10 * FROM CUSTOMER c WHERE c.username = ?1 ORDER BY desc", nativeQuery = true)
    Dashboard refreshCustomerDashboard(String username);

    @Query(value = "SELECT TOP 10 * FROM EMPLOYEE e WHERE c.username = ?1 ORDER BY desc", nativeQuery = true)
    Dashboard refreshEmployeeDashboard(String username);
}
