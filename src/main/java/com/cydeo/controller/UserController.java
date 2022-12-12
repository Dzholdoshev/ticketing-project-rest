package com.cydeo.controller;


import com.cydeo.annotation.ExecutionTime;
import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.exception.TicketingProjectException;
import com.cydeo.service.KeycloakService;
import com.cydeo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping("/api/v1/user")
@Tag(name = "UserController", description = "User API")
public class UserController {

    private final UserService userService;


    public UserController(UserService userService, KeycloakService keycloakService) {
        this.userService = userService;

    }
    @ExecutionTime
    @GetMapping()
    @RolesAllowed({"Manager","Admin"})
    @Operation (summary = "Get list of users")
    public ResponseEntity<ResponseWrapper> getUsers(){
        return  ResponseEntity.ok(new ResponseWrapper("Users found",userService.listAllUsers(), HttpStatus.ACCEPTED));
    }
    @ExecutionTime
    @GetMapping("/{username}")
    @RolesAllowed("Admin")
    @Operation (summary = "Get user by user name")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("username") String name){
        return ResponseEntity.ok(new ResponseWrapper("User by name found", userService.findByUserName(name),HttpStatus.OK));
    }
    @PostMapping()
    @RolesAllowed("Admin")
    @Operation (summary = "Create user")
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){

        userService.save(user);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User is successfully created", HttpStatus.CREATED));
    }
    @PutMapping
    @RolesAllowed({"Admin"})
    @Operation (summary = "Update user")
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.ok(new ResponseWrapper("user updated",HttpStatus.OK));
    }
    @DeleteMapping("/{username}")
    @RolesAllowed({"Admin"})
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String name) throws TicketingProjectException {
        userService.delete(name);
        return ResponseEntity.ok( new ResponseWrapper("User is deleted", HttpStatus.OK));
    }


}
