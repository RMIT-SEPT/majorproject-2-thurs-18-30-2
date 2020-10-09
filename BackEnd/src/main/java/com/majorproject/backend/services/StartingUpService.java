package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class populates the database when the application starts running
 */
@Service
public class StartingUpService {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private BServiceRepository bServiceRepository;
    @Autowired
    private EmployeeScheduleRepository employeeScheduleRepository;
    @Autowired
    private BookingRepository bookingRepository;

    // String to Date format
    private SimpleDateFormat formatterDate = new SimpleDateFormat("dd-MM-yyyy");

    // String to Time format
    private SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm");

    /**
     * Adds business owner when the application starts
     * @param event When spring boot starts
     */
    @EventListener
    public void runApplication(ApplicationReadyEvent event) {
        // Add the business owner
        Employee businessOwner = new Employee("Homeyyy", "Ash", "hAsh@mail.com",
                "hAshAdmin", "hA123", "10 Software Avenue",
                "0123456789", "admin");
        employeeRepository.save(businessOwner);

        // Add 2 customers
        Customer customer1 = new Customer("CusTee", "hehe", "cth@mail.com", "cth",
                "cth123","cth address", "0386376921");
        customerRepository.save(customer1);

        Customer customer2 = new Customer("CusWow", "reee", "cwr@mail.com", "cwr",
                "cwr123","cwr address", "0472573568");
        customerRepository.save(customer2);

        // Add 3 employees
        Employee employee1 = new Employee ("a", "a", "a@mail.com", "a", "a123",
                "a address", "0573296381", "employee");
        employeeRepository.save(employee1);

        Employee employee2 = new Employee ("b", "b", "b@mail.com", "b", "b123",
                "b address", "0683765938", "employee");
        employeeRepository.save(employee2);

        Employee employee3 = new Employee ("c", "c", "c@mail.com", "c", "c123",
                "c address", "0184574819", "employee");
        employeeRepository.save(employee3);

        // Create 2 services
        BService fullBodyMassage = new BService("FullBodyMassage", "Founded and Serviced by Designer Anthony");
        bServiceRepository.save(fullBodyMassage);
        BService dineAndDash = new BService("Dine And Dash", "Founded by Dasher Anthony");
        bServiceRepository.save(dineAndDash);

        // Create 3 employee schedule
        try {
            EmployeeSchedule employeeSchedule1 = new EmployeeSchedule();
            employeeSchedule1.setEmployee(employee1);
            employeeSchedule1.setBService(fullBodyMassage);
//            employeeSchedule1.setBService(bServiceRepository.findByBServiceId(Long.parseLong("1")));
            employeeSchedule1.setDate(formatterDate.parse("20-10-2020"));
            employeeSchedule1.setStartTime(formatterTime.parse("20:00"));
            employeeSchedule1.setEndTime(formatterTime.parse("21:00"));
            employeeSchedule1.setAvailability(false); // Will be booked
            employeeScheduleRepository.save(employeeSchedule1);

            EmployeeSchedule employeeSchedule2 = new EmployeeSchedule();
            employeeSchedule2.setEmployee(employee1);
            employeeSchedule2.setBService(dineAndDash);
//            employeeSchedule1.setBService(bServiceRepository.findByBServiceId(Long.parseLong("2")));
            employeeSchedule2.setDate(formatterDate.parse("21-10-2020"));
            employeeSchedule2.setStartTime(formatterTime.parse("07:00"));
            employeeSchedule2.setEndTime(formatterTime.parse("09:00"));
            employeeSchedule2.setAvailability(false); // Will be booked
            employeeScheduleRepository.save(employeeSchedule2);

            EmployeeSchedule employeeSchedule3 = new EmployeeSchedule();
            employeeSchedule3.setEmployee(employee2);
            employeeSchedule3.setBService(dineAndDash);
//            employeeSchedule1.setBService(bServiceRepository.findByBServiceId(Long.parseLong("2")));
            employeeSchedule3.setDate(formatterDate.parse("21-10-2020"));
            employeeSchedule3.setStartTime(formatterTime.parse("08:00"));
            employeeSchedule3.setEndTime(formatterTime.parse("10:00"));
            employeeSchedule3.setAvailability(true); // Not booked
            employeeScheduleRepository.save(employeeSchedule3);

            EmployeeSchedule employeeSchedule4 = new EmployeeSchedule();
            employeeSchedule4.setEmployee(employee2);
            employeeSchedule4.setBService(dineAndDash);
            employeeSchedule4.setDate(formatterDate.parse("21-10-2020"));
            employeeSchedule4.setStartTime(formatterTime.parse("08:00"));
            employeeSchedule4.setEndTime(formatterTime.parse("10:00"));
            employeeSchedule4.setAvailability(true); // Not booked
            employeeScheduleRepository.save(employeeSchedule4);

            // Add 2 bookings
            Booking booking1 = new Booking(employeeSchedule1, customer1);
            bookingRepository.save(booking1);

            Booking booking2 = new Booking(employeeSchedule2, customer2);
            bookingRepository.save(booking2);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
