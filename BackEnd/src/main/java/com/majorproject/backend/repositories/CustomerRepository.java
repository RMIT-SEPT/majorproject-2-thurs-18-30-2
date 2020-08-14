package com.majorproject.backend.repositories;

import com.majorproject.backend.models.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends CrudRepository<Customer, Long> {

    @Override
    Iterable<Customer> findAllById(Iterable<Long> iterable);

    @Query(value = "SELECT * FROM CUSTOMER c WHERE c.email = ?1",
            nativeQuery = true)
    Customer findByEmail(String email);
}