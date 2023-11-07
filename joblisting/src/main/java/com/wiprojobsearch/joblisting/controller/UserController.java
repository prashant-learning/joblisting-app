package com.wiprojobsearch.joblisting.controller;


import com.wiprojobsearch.joblisting.model.LoginRequest;
import com.wiprojobsearch.joblisting.model.User;
import com.wiprojobsearch.joblisting.repository.UserRepository;
import com.wiprojobsearch.joblisting.service.UserAuthnOperationService;
import com.wiprojobsearch.joblisting.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;


    @Autowired
    private UserAuthnOperationService userAuthnOperationService;

    @Autowired
    private JwtUtil jwtUtil;

    // ... Other endpoints for user-related operations (e.g., login, delete) may exist here

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Validation errors occurred, return error response
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errors);
        }

        // Check if the email is already registered
        if (userRepository.existsByEmail(user.getEmail())) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("message", "Email is already in use");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }

        // Save the user if validation passes and the email is not already registered
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        User exsistingUser = userAuthnOperationService.getByEmail(loginRequest.getEmail());

        if(exsistingUser == null){
          throw new UsernameNotFoundException("User doesn't exist");
        }
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("Authorization", jwtUtil.generateToken(exsistingUser))
                .body(userAuthnOperationService.loginUser(loginRequest));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId) {
        try {
            // Check if the user exists
            if (userRepository.existsById(userId)) {
                // User exists, delete them
                userRepository.deleteById(userId);
                return ResponseEntity.ok("User deleted successfully");
            } else {
                // User not found
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
            }
        } catch (Exception e) {
            // Handle exceptions, e.g., database errors
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting user");
        }
    }


}
