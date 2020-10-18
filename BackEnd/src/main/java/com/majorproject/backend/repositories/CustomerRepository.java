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
     * This query returns a customer based on the email entered by the user
     * @param email The email entered by the user
     * @return A customer if the customer exists, otherwise null
     */
    @Query(value = "SELECT * FROM Customer c WHERE c.email = ?1", nativeQuery = true)
    Customer findByEmail(String email);

    /**
     * This query returns a customer based on the username and password entered by the user
     * It is used to check for credential validation
     * @param username The username entered by the user
     * @param password The password entered by the user
     * @return A customer if the customer exists, otherwise null
     */
    @Query(value = "SELECT * FROM Customer c WHERE c.username = ?1 AND c.password = ?2", nativeQuery = true)
    Customer findByUsernameAndPassword(String username, String password);

    /**
     * This query returns a customer based on the username entered by the user
     * @param username The email entered by the user
     * @return A customer if the customer exists, otherwise null
     */
    @Query(value = "SELECT * FROM Customer c WHERE c.username = ?1", nativeQuery = true)
    Customer findByUsername(String username);

    /**
     * This query returns a customer based on the is entered by the user
     * @param customerId The customer id entered by the user
     * @return A customer if the customer exists, otherwise null
     */
    @Query(value = "SELECT * FROM Customer c WHERE c.id = ?1", nativeQuery = true)
    Customer findByCustomerId(long customerId);
}