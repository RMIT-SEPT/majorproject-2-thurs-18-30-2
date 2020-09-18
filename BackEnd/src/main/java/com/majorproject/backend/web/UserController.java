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

    @GetMapping("/usernameExists/{username}")
    public ResponseEntity<String> userNameExists(@PathVariable String username) {
        String exists = userService.userNameExists(username) ? "true" : "false";
        return new ResponseEntity<String>(exists, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginVerification(@RequestBody LoginForm loginForm) {
        User user = userService.loginUser(loginForm);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
