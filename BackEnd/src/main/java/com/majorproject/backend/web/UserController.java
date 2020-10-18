package com.majorproject.backend.web;

import com.majorproject.backend.exceptions.ResponseException;
import com.majorproject.backend.responseForms.JWTLoginSuccessResponse;
import com.majorproject.backend.responseForms.LoginForm;
import com.majorproject.backend.models.User;
import com.majorproject.backend.security.JwtTokenProvider;
import com.majorproject.backend.services.CustomUserDetailsService;
import com.majorproject.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.majorproject.backend.security.SecurityConstants.TOKEN_PREFIX;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    CustomUserDetailsService customUserDetailsService;


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
        User user = userService.getUserByUserName(loginForm.getUsername());
        String jwt = "";
        try {
            Authentication authentication = null;

            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginForm.getUsername(),
                            loginForm.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            jwt = TOKEN_PREFIX + tokenProvider.generateToken(authentication);
        } catch(Exception e) {
            throw new ResponseException(HttpStatus.UNAUTHORIZED, "Username or password is invalid");
        }

        return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt, user));
    }

    /**
     * Gets a user based on the user id entered by the user
     * @param id The user id entered by the user
     * @return The user object
     */
    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = customUserDetailsService.loadUserById(id);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    /**
     * Gets a user type based on the user id entered by the user
     * @param id The user id entered by the user
     * @return The user type
     */
    @GetMapping("/getUserType/{id}")
    public ResponseEntity<?> getUserType(@PathVariable Long id) {
        String type = userService.getUserType(id);
        Map<String, String> response = new HashMap<String, String>();
        response.put("type", type);
        return new ResponseEntity<Map>(response, HttpStatus.OK);
    }
}
