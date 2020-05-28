package com.apiit.bangerandco.controllers;

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

    @PostMapping("/GetUserList")
    public ResponseEntity<List<UserDTO>> getUserList(){
        return userService.getUserList();
    }

    @DeleteMapping("/DeleteUser/{id}")
    public ResponseEntity<Boolean> DeleteUser(@PathVariable String id){
        return userService.deleteUser(id);
    }

    @PutMapping("/UpdateUser/{id}")
    public ResponseEntity<User> UpdateUser(@PathVariable String id, @RequestBody User user){
        return userService.updateUser(id,user);
    }

    @PutMapping("/UpdateUserNIC/{id}")
    public ResponseEntity<User> UpdateUserNIC(@PathVariable String id, @RequestBody User user){
        return userService.updateUserNIC(id,user);
    }

    @PostMapping("/BlacklistUser/{id}")
    public void BlacklistUser(@PathVariable String id){
        userService.blacklistUser(id);
    }

//    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity uploadFile(@RequestParam MultipartFile file) {
//        logger.info(String.format("File name '%s' uploaded successfully.", file.getOriginalFilename()));
//        return ResponseEntity.ok().build();
//    }
//
//    @RequestMapping("/download")
//    public ResponseEntity downloadFile1(@RequestParam String fileName) throws IOException {
//
//        File file = new File(fileName);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .contentLength(file.length())
//                .body(resource);
//    }
}
