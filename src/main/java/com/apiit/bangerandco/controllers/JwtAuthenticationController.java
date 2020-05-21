package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.JwtUserDetailsService;
import com.apiit.bangerandco.config.JwtTokenUtil;
import com.apiit.bangerandco.dtos.UserDTO;
import com.apiit.bangerandco.enums.CustomerState;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @Autowired
    private UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @PostMapping(value = "/authenticate")
    public ResponseEntity<UserDTO> createAuthenticationToken(@RequestBody UserDTO authenticationRequest) throws Exception {
        authenticate(authenticationRequest.getEmail(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        Optional<User> userOptional = userRepo.findById(authenticationRequest.getEmail());
        if(userOptional.isPresent()){
            User user = userOptional.get();

            if( user.getCustomerState()!= CustomerState.Blacklisted){
                UserDTO userDTO=new UserDTO(token,user.getFirstName(),user.getLastName());

                return ResponseEntity.ok(userDTO);
            }else{
                return new ResponseEntity<>(null, HttpStatus.FORBIDDEN);
            }


        }else{
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
