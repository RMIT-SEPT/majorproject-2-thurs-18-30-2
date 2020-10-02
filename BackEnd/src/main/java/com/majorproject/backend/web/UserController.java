package com.majorproject.backend.web;

import com.majorproject.backend.responseForms.LoginForm;
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

    /**
     * Checks if the username exists.
     * @param usernameAPI The username of the user
     * @return A response entity if the username exists
     */
    @GetMapping("/usernameExists/{usernameAPI}")
    public ResponseEntity<?> usernameExists(@PathVariable String usernameAPI) {
        String exists = userService.checkIfUsernameExists(usernameAPI);
        return new ResponseEntity<String>(exists, HttpStatus.OK);
    }

    /**
     * User logins and checks if the credentials are correct
     * @param loginForm The login details entered by the user
     * @return A response entity when the login is successful
     */
    @PostMapping("/login")
    public ResponseEntity<?> loginVerification(@RequestBody LoginForm loginForm) {
        User user = userService.loginUser(loginForm);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
