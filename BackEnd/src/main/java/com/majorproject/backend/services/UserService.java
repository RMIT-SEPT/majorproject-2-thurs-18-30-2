package com.majorproject.backend.services;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.models.User;
import com.majorproject.backend.repositories.CustomerRepository;
import com.majorproject.backend.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private CustomerRepository customerRepository;

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

    public boolean userNameExists(String username) {
        boolean exists = true;
        if (employeeRepository.findByUsername(username) == null &&
                customerRepository.findByUsername(username) == null ) {
            exists = false;
        }

        return exists;
    }

}
