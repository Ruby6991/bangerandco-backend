package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.dtos.BookingDTO;
import com.apiit.bangerandco.dtos.UserDTO;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

//    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @PostMapping("/Register")
    public ResponseEntity<Boolean> RegisterUser(@RequestBody User user){
        return userService.registerUser(user);
    }

    @PostMapping("/GetUser")
    public ResponseEntity<UserDTO> GetUserDetails(@RequestBody User user){
        return userService.getUser(user.getEmail());
    }

    @PostMapping("/GetUserBookings")
    public ResponseEntity<List<BookingDTO>> getUserBookings(@RequestBody User user){
        return userService.getUserBookingList(user.getEmail());
    }

    @PostMapping("/GetUserList")
    public ResponseEntity<List<UserDTO>> getUserList(){
        return userService.getUserList();
    }

    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<Boolean> DeleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @PutMapping("/UpdateUser/{id}")
    public ResponseEntity<UserDTO> UpdateUser(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id,user);
    }

    @PutMapping("/UpdatePassword/{id}")
    public ResponseEntity<UserDTO> UpdatePassword(@PathVariable String id, @RequestParam(value="currentPsw") String currentPsw, @RequestParam(value="newPsw") String newPsw){
        return userService.updatePassword(id, currentPsw ,newPsw);
    }

    @PutMapping("/UpdateUserNIC/{id}")
    public ResponseEntity<UserDTO> UpdateUserNIC(@PathVariable String id, @RequestBody User user){
        return userService.updateUserNIC(id,user);
    }

    @PostMapping("/BlacklistUser/{id}")
    public void BlacklistUser(@PathVariable String id){
        userService.blacklistUser(id);
    }

}
