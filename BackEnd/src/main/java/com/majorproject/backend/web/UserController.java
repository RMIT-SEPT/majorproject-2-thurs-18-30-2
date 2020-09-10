package com.majorproject.backend.web;

import com.majorproject.backend.jsonconv.LoginForm;
import com.majorproject.backend.models.Employee;
import com.majorproject.backend.models.User;
import com.majorproject.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/registeringCheck/username")
    public ResponseEntity<String> checkIfUsernameExists(String username) {
        ResponseEntity<String> responseEntity = null;
        boolean duplicated = userService.checkForDuplicateUsername(username);

        if(duplicated) {
            responseEntity = new ResponseEntity<String>("Username exists", HttpStatus.NOT_ACCEPTABLE);
        } else {
            responseEntity = new ResponseEntity<String>("Username good", HttpStatus.OK);
        }

        return responseEntity;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginVerification(@RequestBody LoginForm loginForm) {
        ResponseEntity<?> responseEntity = null;
        User user = userService.loginUser(loginForm);
        if(user != null) {
            responseEntity = new ResponseEntity<User>(user, HttpStatus.OK);
        } else {
            responseEntity = new ResponseEntity<String>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        }

        return responseEntity;
    }
}
