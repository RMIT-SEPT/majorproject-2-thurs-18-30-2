package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.models.Customer;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.models.User;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;


    /**
     * Checks if the login credentials are correct
     * @param loginForm The login details entered by the user
     * @return The user object if credentials are correct, else throw ResponseException
     */
    public User loginUser(LoginForm loginForm) {
        User user = null;
        String username = loginForm.getUsername();
        String password = loginForm.getPassword();
        // Check if user is employee or customer or none
        user = employeeRepository.findByUsernameAndPassword(username, password);
        if(user == null) user = customerRepository.findByUsernameAndPassword(username, password);

        if(user == null) throw new ResponseException(HttpStatus.UNAUTHORIZED, "Username or password is invalid");

        return user;
    }

//    /**
//     * Checks if the username exists
//     * @param username The username of the user
//     * @return A boolean that states if the username exists
//     * If it exists, return true
//     * else return false
//     */
//    public boolean userNameExists(String username) {
//        boolean exists = false;
//
//        if(employeeRepository.findByUsername(username) != null ||
//            customerRepository.findByUsername(username) != null) {
//            exists = true;
//        }
//
//        return exists;
//    }

    /**
     * Checks if the username exists
     * @param usernameAPI The username
     * @return A string that states if the username exists
     */
    public String checkIfUsernameExists(String usernameAPI) {
        String exists;
        boolean result = false;

        if(employeeRepository.findByUsername(usernameAPI) != null ||
                customerRepository.findByUsername(usernameAPI) != null) {
            result = true;
        }

        if(result) { // if result is true
            exists = "true";
        } else { // if result is false
            exists = "false";
        }

        return exists;
    }

    /**
     * Gets user type based on user id
     * @param id The user id
     * @return The user type based on the user id
     */
    public String getUserType(Long id) {
        String type;
        Customer customer = customerRepository.findByCustomerId(id);
        if(customer == null) {
            Employee employee = employeeRepository.findByEmployeeId(id);
            if(employee == null) throw new ResponseException(HttpStatus.NOT_FOUND, "User Id does not exist");
            type = employee.getEmpType();
        } else {
            type = "customer";
        }
        return type;
    }

    /**
     * Gets user based on username
     * @param username The username
     * @return The user based on the username
     */
    public User getUserByUserName(String username) {
        User user = null;
        user = customerRepository.findByUsername(username);
        if(user == null) user = employeeRepository.findByUsername(username);
        return user;
    }
}
