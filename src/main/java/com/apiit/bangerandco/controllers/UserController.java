package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.dtos.BookingDTO;
import com.apiit.bangerandco.dtos.UserDTO;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class UserController {

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

    @PostMapping("/CheckUser")
    public String CheckUser(@RequestBody User user){
        return userService.checkUser(user);
    }

    @PostMapping("/CheckUserFraud")
    public boolean CheckUserFraud(@RequestBody User user){
        return userService.checkUserFraud(user.getDriversLicense(),user.getEmail());
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

    @PutMapping("/UpdateUserLicense/{id}")
    public ResponseEntity<UserDTO> UpdateUserLicense(@PathVariable String id, @RequestBody User user){
        return userService.updateUserLicense(id,user);
    }

    @PutMapping("/UpdateUserState/{id}")
    public ResponseEntity<UserDTO> UpdateUserState(@PathVariable String id, @RequestBody User user){
        return userService.updateUserState(id,user);
    }

}
