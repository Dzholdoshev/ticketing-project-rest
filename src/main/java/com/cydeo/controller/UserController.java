package com.cydeo.controller;


import com.cydeo.dto.ResponseWrapper;
import com.cydeo.dto.UserDTO;
import com.cydeo.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseWrapper> getUsers(){
        return  ResponseEntity.ok(new ResponseWrapper("Users found",userService.listAllUsers(), HttpStatus.ACCEPTED));
    }
    @GetMapping("/{username}")
    public ResponseEntity<ResponseWrapper> getUserByUserName(@PathVariable("username") String name){
        return ResponseEntity.ok(new ResponseWrapper("User by name found", userService.findByUserName(name),HttpStatus.OK));
    }
    @PostMapping()
    public ResponseEntity<ResponseWrapper> createUser(@RequestBody UserDTO user){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseWrapper("User created", HttpStatus.CREATED));
    }
    @PutMapping
    public ResponseEntity<ResponseWrapper> updateUser(@RequestBody UserDTO user){
        userService.save(user);
        return ResponseEntity.ok(new ResponseWrapper("user updated",HttpStatus.OK));
    }
    @DeleteMapping("/{username}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable("username") String name){
        userService.delete(name);
        return ResponseEntity.ok( new ResponseWrapper("User is deleted", HttpStatus.OK));
    }


}
