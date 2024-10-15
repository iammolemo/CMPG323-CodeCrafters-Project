package com.S2T.Share_2_Teach.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.S2T.Share_2_Teach.dto.RequestResponse;
import com.S2T.Share_2_Teach.entity.AppUsers;
import com.S2T.Share_2_Teach.service.FileStorageService;
import com.S2T.Share_2_Teach.service.UsersManagementService;

@RestController // Indicates that this class is a REST controller
public class UserController {

    @Autowired
    private UsersManagementService usersManagementService; // Service for user management operations

    @Autowired
    public FileStorageService fileStorageService;

    public UserController() {
    }

    // Handles user registration requests
    @PostMapping("/auth/register")
    public ResponseEntity<RequestResponse> register(@RequestBody RequestResponse reg){
        // Calls the register method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.register(reg));
    }

    // Handles user login requests
    @PostMapping("/auth/login")
    public ResponseEntity<RequestResponse> login(@RequestBody RequestResponse log){
        // Calls the login method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.login(log));
    }

    // Handles token refresh requests
    @PostMapping("/auth/refresh")
    public ResponseEntity<RequestResponse> refreshToken(@RequestBody RequestResponse req){
        // Calls the refreshToken method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.refreshToken(req));
    }

    // Retrieves all users (admin access)
    @GetMapping("/admin/get-all-users")
    public ResponseEntity<RequestResponse> getAllUsers(){
        // Calls the getAllUsers method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.getAllUsers());
    }

    // Retrieves a specific user by ID (admin access)
    @GetMapping("/admin/get-users/{userId}")
    public ResponseEntity<RequestResponse> getUserById(@PathVariable Integer userId){
        // Calls the getUserById method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.getUserById(userId));
    }

    // Updates user details by ID (admin access)
    @PutMapping("/admin/update/{userId}")
    public ResponseEntity<RequestResponse> updateUser(@PathVariable Integer userId, @RequestBody AppUsers requestResponse){
        // Calls the userUpdate method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.userUpdate(userId, requestResponse));
    }

    // Retrieves the profile of the currently authenticated user (admin or user access)
    @GetMapping("/adminuser/get-profile")
    public ResponseEntity<RequestResponse> getProfile(){
        // Gets the current authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName(); // Extracts the email of the authenticated user
        RequestResponse response = usersManagementService.getUserInfo(email); // Retrieves user info
        // Returns the user info with the appropriate status code
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    // Deletes a user by ID (admin access)
    @DeleteMapping("/admin/delete/{userId}")
    public ResponseEntity<RequestResponse> deleteUser(@PathVariable Integer userId){
        // Calls the deleteUser method of UsersManagementService and returns the response
        return ResponseEntity.ok(usersManagementService.deleteUser(userId));
    }
}