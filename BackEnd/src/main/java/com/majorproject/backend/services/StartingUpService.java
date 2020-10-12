package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.*;
import com.majorproject.backend.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        businessOwner.setPassword(bCryptPasswordEncoder.encode(businessOwner.getPassword()));
        employeeRepository.save(businessOwner);

        // Add 10 customers
        Customer customer1 = new Customer("CusTee", "hehe", "cth@mail.com", "cth",
                "cth123","cth address", "0386376921");
        customerRepository.save(customer1);

        Customer customer2 = new Customer("CusWow", "reee", "cwr@mail.com", "cwr",
                "cwr123","cwr address", "0472573568");
        customerRepository.save(customer2);

        Customer customer3 = new Customer("Anty", "Ant", "antt@mail.com", "ant",
                "ant123","ant address", "0437295783");
        customerRepository.save(customer3);

        Customer customer4 = new Customer("Dany", "Dan", "dan@mail.com", "dan",
                "dan123","dan address", "0468293059");
        customerRepository.save(customer4);

        Customer customer5 = new Customer("Chingy", "Chin", "ching@mail.com", "chin",
                "chin123","chin address", "0449238493");
        customerRepository.save(customer5);

        Customer customer6 = new Customer("Rez", "Rez", "rez@mail.com", "rez",
                "rez123","rez address", "0496829303");
        customerRepository.save(customer6);

        Customer customer7 = new Customer("Jacob", "Apple", "jacob@mail.com", "jacob",
                "jacob123","jacob address", "0484039182");
        customerRepository.save(customer7);

        Customer customer8 = new Customer("Rachel", "Holt", "rac@mail.com", "rac",
                "rac123","rac address", "0493829391");
        customerRepository.save(customer8);

        Customer customer9 = new Customer("Julia", "Rasins", "jul@mail.com", "jul",
                "jul123","jul address", "0499506928");
        customerRepository.save(customer9);

        Customer customer10 = new Customer("Teny", "Ten", "ten@mail.com", "ten",
                "ten123","ten address", "0493929381");
        customerRepository.save(customer10);


        // Add 10 employees
        Employee employee1 = new Employee ("a", "a", "a@mail.com", "a", "a123",
                "a address", "0573296381", "employee");
        employeeRepository.save(employee1);

        Employee employee2 = new Employee ("b", "b", "b@mail.com", "b", "b123",
                "b address", "0683765938", "employee");
        employeeRepository.save(employee2);

        Employee employee3 = new Employee ("c", "c", "c@mail.com", "c", "c123",
                "c address", "0184574819", "employee");
        employeeRepository.save(employee3);

        Employee employee4 = new Employee ("d", "d", "d@mail.com", "d", "d123",
                "d address", "0278183928", "employee");
        employeeRepository.save(employee4);

        Employee employee5 = new Employee ("e", "e", "e@mail.com", "e", "e123",
                "e address", "0192837484", "employee");
        employeeRepository.save(employee5);

        Employee employee6 = new Employee ("f", "f", "f@mail.com", "f", "f123",
                "f address", "0987348292", "employee");
        employeeRepository.save(employee6);

        Employee employee7 = new Employee ("g", "g", "g@mail.com", "g", "g123",
                "g address", "0494202391", "employee");
        employeeRepository.save(employee7);

        Employee employee8 = new Employee ("h", "h", "h@mail.com", "h", "h123",
                "h address", "0381293023", "employee");
        employeeRepository.save(employee8);

        Employee employee9 = new Employee ("i", "i", "i@mail.com", "i", "i123",
                "i address", "0192838292", "employee");
        employeeRepository.save(employee9);

        Employee employee10 = new Employee ("j", "j", "j@mail.com", "j", "j123",
                "j address", "0983191201", "employee");
        employeeRepository.save(employee10);


        // Create 5 services
        BService fullBodyMassage = new BService("Full Body Massage", "Founded and Serviced by Designer Anthony");
        bServiceRepository.save(fullBodyMassage);

        BService dineAndDash = new BService("Dine And Dash", "Founded by Dasher Anthony");
        bServiceRepository.save(dineAndDash);

        BService cooking = new BService("Cooking", "Founded by Self-Proclaimed 8-Star Chef Anthony");
        bServiceRepository.save(cooking);

        BService learningCoding = new BService("Learning Coding", "Founded by Coder Anthony");
        bServiceRepository.save(learningCoding);

        BService fixingServices = new BService("Fixing Service", "Founded by Demolition Amateur Anthony");
        bServiceRepository.save(fixingServices);


        // Create 10 employee schedule
        try {
            EmployeeSchedule employeeSchedule1 = new EmployeeSchedule();
            employeeSchedule1.setEmployee(employee1);
            employeeSchedule1.setBService(fullBodyMassage);
            employeeSchedule1.setDate(formatterDate.parse("20-09-2020"));
            employeeSchedule1.setStartTime(formatterTime.parse("20:00"));
            employeeSchedule1.setEndTime(formatterTime.parse("21:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule1);

            EmployeeSchedule employeeSchedule2 = new EmployeeSchedule();
            employeeSchedule2.setEmployee(employee1);
            employeeSchedule2.setBService(dineAndDash);
            employeeSchedule2.setDate(formatterDate.parse("21-09-2020"));
            employeeSchedule2.setStartTime(formatterTime.parse("07:00"));
            employeeSchedule2.setEndTime(formatterTime.parse("09:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule2);

            EmployeeSchedule employeeSchedule3 = new EmployeeSchedule();
            employeeSchedule3.setEmployee(employee2);
            employeeSchedule3.setBService(dineAndDash);
            employeeSchedule3.setDate(formatterDate.parse("21-09-2020"));
            employeeSchedule3.setStartTime(formatterTime.parse("08:00"));
            employeeSchedule3.setEndTime(formatterTime.parse("10:00"));
            employeeSchedule3.setAvailability(true);
            employeeScheduleRepository.save(employeeSchedule3);

            EmployeeSchedule employeeSchedule4 = new EmployeeSchedule();
            employeeSchedule4.setEmployee(employee6);
            employeeSchedule4.setBService(cooking);
            employeeSchedule4.setDate(formatterDate.parse("22-09-2020"));
            employeeSchedule4.setStartTime(formatterTime.parse("17:00"));
            employeeSchedule4.setEndTime(formatterTime.parse("18:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule4);

            EmployeeSchedule employeeSchedule5 = new EmployeeSchedule();
            employeeSchedule5.setEmployee(employee4);
            employeeSchedule5.setBService(learningCoding);
            employeeSchedule5.setDate(formatterDate.parse("22-09-2020"));
            employeeSchedule5.setStartTime(formatterTime.parse("18:00"));
            employeeSchedule5.setEndTime(formatterTime.parse("19:00"));
            employeeSchedule5.setAvailability(true);
            employeeScheduleRepository.save(employeeSchedule5);

            EmployeeSchedule employeeSchedule6 = new EmployeeSchedule();
            employeeSchedule6.setEmployee(employee7);
            employeeSchedule6.setBService(learningCoding);
            employeeSchedule6.setDate(formatterDate.parse("21-09-2020"));
            employeeSchedule6.setStartTime(formatterTime.parse("06:00"));
            employeeSchedule6.setEndTime(formatterTime.parse("07:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule6);

            EmployeeSchedule employeeSchedule7 = new EmployeeSchedule();
            employeeSchedule7.setEmployee(employee7);
            employeeSchedule7.setBService(fixingServices);
            employeeSchedule7.setDate(formatterDate.parse("20-09-2020"));
            employeeSchedule7.setStartTime(formatterTime.parse("05:00"));
            employeeSchedule7.setEndTime(formatterTime.parse("09:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule7);

            EmployeeSchedule employeeSchedule8 = new EmployeeSchedule();
            employeeSchedule8.setEmployee(employee10);
            employeeSchedule8.setBService(cooking);
            employeeSchedule8.setDate(formatterDate.parse("22-09-2020"));
            employeeSchedule8.setStartTime(formatterTime.parse("18:00"));
            employeeSchedule8.setEndTime(formatterTime.parse("22:00"));
            employeeSchedule8.setAvailability(true);
            employeeScheduleRepository.save(employeeSchedule8);

            EmployeeSchedule employeeSchedule9 = new EmployeeSchedule();
            employeeSchedule9.setEmployee(employee2);
            employeeSchedule9.setBService(cooking);
            employeeSchedule9.setDate(formatterDate.parse("22-09-2020"));
            employeeSchedule9.setStartTime(formatterTime.parse("09:00"));
            employeeSchedule9.setEndTime(formatterTime.parse("11:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule9);

            EmployeeSchedule employeeSchedule10 = new EmployeeSchedule();
            employeeSchedule10.setEmployee(employee7);
            employeeSchedule10.setBService(fixingServices);
            employeeSchedule10.setDate(formatterDate.parse("22-09-2020"));
            employeeSchedule10.setStartTime(formatterTime.parse("16:00"));
            employeeSchedule10.setEndTime(formatterTime.parse("18:00"));
            employeeSchedule1.setAvailability(false); // Booked in booking below
            employeeScheduleRepository.save(employeeSchedule10);


            // Add 7 bookings
            Booking booking1 = new Booking(employeeSchedule1, customer1);
            bookingRepository.save(booking1);

            Booking booking2 = new Booking(employeeSchedule2, customer2);
            bookingRepository.save(booking2);

            Booking booking3 = new Booking(employeeSchedule4, customer4);
            bookingRepository.save(booking3);

            Booking booking4 = new Booking(employeeSchedule6, customer7);
            bookingRepository.save(booking4);

            Booking booking5 = new Booking(employeeSchedule7, customer7);
            bookingRepository.save(booking5);

            Booking booking6 = new Booking(employeeSchedule9, customer4);
            bookingRepository.save(booking6);

            Booking booking7 = new Booking(employeeSchedule10, customer9);
            bookingRepository.save(booking7);
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
