package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    @Override
    Iterable<Customer> findAllById(Iterable<Long> iterable);

    /**
     * This query returns a customer if a
     * @param email
     * @return
     */
    @Query(value = "SELECT * FROM CUSTOMER c WHERE c.email = ?1", nativeQuery = true)
    Customer findByEmail(String email);

    @Query(value = "SELECT * FROM CUSTOMER c WHERE c.username = ?1 AND c.password = ?2", nativeQuery = true)
    Customer findByUsernameAndPassword(String username, String password);

    @Query(value = "SELECT * FROM CUSTOMER c WHERE c.username = ?1", nativeQuery = true)
    Customer findByUsername(String username);
}