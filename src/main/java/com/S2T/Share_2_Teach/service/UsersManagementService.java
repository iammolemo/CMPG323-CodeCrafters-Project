package com.S2T.Share_2_Teach.service;


import com.S2T.Share_2_Teach.dto.RequestResponse;
import com.S2T.Share_2_Teach.entity.AppUsers;
import com.S2T.Share_2_Teach.repository.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
public class UsersManagementService {

    @Autowired
    private UsersRepo usersRepo; // Repository for user data access

    @Autowired
    private JWTUtils jwtUtils; // Utility for JWT operations

    @Autowired
    private AuthenticationManager authenticationManager; // Manages authentication

    @Autowired
    private PasswordEncoder passwordEncoder; // Handles password encoding

    // Registers a new user
    public RequestResponse register(RequestResponse registrationRequest) {
        RequestResponse response = new RequestResponse();

        try {
            AppUsers appUser = new AppUsers();
            appUser.setEmail(registrationRequest.getEmail());
            appUser.setName(registrationRequest.getName());
            appUser.setRole(registrationRequest.getRole());
            appUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
            AppUsers appUsersResult = usersRepo.save(appUser);
            if (appUsersResult.getId() > 0) {
                response.setAppUsers(appUsersResult);
                response.setMessage("User Successfully Saved.");
                response.setStatusCode(200);
            }

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

    // Authenticates user and generates JWT tokens
    public RequestResponse login(RequestResponse loginRequest) {
        RequestResponse resp = new RequestResponse();
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = usersRepo.findByEmail(loginRequest.getEmail()).orElseThrow();
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            resp.setStatusCode(200);
            resp.setToken(jwt);
            resp.setRefreshToken(refreshToken);
            resp.setExpirationTime("2Hrs");
            resp.setMessage("Successfully Logged In");

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    // Refreshes JWT token
    public RequestResponse refreshToken(RequestResponse refreshTokenRequest) {
        RequestResponse resp = new RequestResponse();
        try {
            String uEmail = jwtUtils.extractUsername(refreshTokenRequest.getToken());
            AppUsers users = usersRepo.findByEmail(uEmail).orElseThrow();
            if (jwtUtils.isTokenValid(refreshTokenRequest.getToken(), users)) {
                var jwt = jwtUtils.generateToken(users);
                resp.setStatusCode(200);
                resp.setToken(jwt);
                resp.setRefreshToken(refreshTokenRequest.getRefreshToken());
                resp.setExpirationTime("24Hrs");
                resp.setMessage("Successfully Refreshed Token");
            }

        } catch (Exception e) {
            resp.setStatusCode(500);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    // Retrieves all users
    public RequestResponse getAllUsers() {
        RequestResponse requestResponse = new RequestResponse();

        try {
            List<AppUsers> result = usersRepo.findAll();
            if(!result.isEmpty()) {
                requestResponse.setAppUsersList(result);
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("Successful");
            }else{
                requestResponse.setStatusCode(404);
                requestResponse.setMessage("No users found");
            }
            return requestResponse;
        }catch (Exception e) {
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("An error occured: "+ e.getMessage());
            return requestResponse;
        }
    }

    // Retrieves user by ID
    public RequestResponse getUserById(Integer id) {
        RequestResponse requestResponse = new RequestResponse();
        try {
            AppUsers userById = usersRepo.findById(id).orElseThrow(() -> new RuntimeException("User was not found."));
            requestResponse.setAppUsers(userById);
            requestResponse.setStatusCode(200);
            requestResponse.setMessage("User with id " + id + " was successfully found.");
        } catch (Exception e){
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("An error occurred: "+ e.getMessage());
        }
        return requestResponse;
    }

    // Deletes a user by ID
    public RequestResponse deleteUser(Integer userId){
        RequestResponse requestResponse = new RequestResponse();
        try {
            Optional<AppUsers> userOptional = usersRepo.findById(userId);
            if (userOptional.isPresent()) {
                usersRepo.deleteById(userId);
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("User successfully deleted");
            } else {
                requestResponse.setStatusCode(404);
                requestResponse.setMessage("User was not found for deletion");
            }
        } catch (Exception e) {
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("Error occurred while attempting to delete user: " + e.getMessage());
        }
        return requestResponse;
    }

    // Updates user details
    public RequestResponse userUpdate(Integer userId, AppUsers updatedUser){
        RequestResponse requestResponse = new RequestResponse();
        try{
            Optional<AppUsers> usersOptional = usersRepo.findById(userId);
            if(usersOptional.isPresent()) {
                AppUsers existingUser = usersOptional.get();
                existingUser.setEmail(updatedUser.getEmail());
                existingUser.setName(updatedUser.getName());
                existingUser.setRole(updatedUser.getRole());

                // Check if password is present in the request
                if(updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
                    // Encode password and update it
                    existingUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
                }
                AppUsers savedUser = usersRepo.save(existingUser);
                requestResponse.setAppUsers(savedUser);
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("User was successfully updated.");
            }
        } catch(Exception e){
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("Error occurred while attempting to update user: " + e.getMessage());
        }
        return requestResponse;
    }

    // Retrieves user information by email
    public RequestResponse getUserInfo(String email){
        RequestResponse requestResponse = new RequestResponse();
        try {
            Optional<AppUsers> usersOptional = usersRepo.findByEmail(email);
            if (usersOptional.isPresent()) {
                requestResponse.setAppUsers(usersOptional.get());
                requestResponse.setStatusCode(200);
                requestResponse.setMessage("Successful");
            } else {
                requestResponse.setStatusCode(404);
                requestResponse.setMessage("User was not found");
            }
        } catch(Exception e){
            requestResponse.setStatusCode(500);
            requestResponse.setMessage("An error occurred while attempting to get user info: " + e.getMessage());
        }
        return requestResponse;
    }
}

