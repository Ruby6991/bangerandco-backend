package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.BookingDTO;
import com.apiit.bangerandco.dtos.UserDTO;
import com.apiit.bangerandco.enums.CustomerState;
import com.apiit.bangerandco.enums.UserType;
import com.apiit.bangerandco.models.Booking;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    private ModelToDTO modelToDTO;

    public ResponseEntity<Boolean> registerUser(User newUser){
        Optional<User> userOptional=userRepo.findById(newUser.getEmail());
        if(!userOptional.isPresent()){
            newUser.setUserType(UserType.Customer);
            newUser.setCustomerState(CustomerState.New);
            newUser.setPassword(bcryptEncoder.encode(newUser.getPassword()));
            userRepo.save(newUser);
            return new ResponseEntity<>(true,HttpStatus.OK);

        }
        return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<UserDTO> getUser(String id){
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            return new ResponseEntity<>(modelToDTO.userToDTO(user), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<BookingDTO>> getUserBookingList(String id) {
        List<BookingDTO> userBookingDTOs = new ArrayList<>();
        Optional<User> userOptional = userRepo.findById(id);
        User user = userOptional.get();
        List<Booking> userBookings = user.getBookings();
        if(!userBookings.isEmpty()){
            for(Booking booking : userBookings){
                userBookingDTOs.add(modelToDTO.bookingToDTO(booking));
            }
            return new ResponseEntity<>(userBookingDTOs, HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Boolean> deleteUser(String id){
        try{
            userRepo.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    public ResponseEntity<UserDTO> updateUser(String id, User newUser){
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setAddress(newUser.getAddress());
//            user.setEmail(newUser.getEmail());
            user.setFirstName(newUser.getFirstName());
            user.setLastName(newUser.getLastName());
            user.setPhoneNo(newUser.getPhoneNo());
            user.setDateOfBirth(newUser.getDateOfBirth());
            userRepo.save(user);
            return new ResponseEntity<>(modelToDTO.userToDTO(user),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserDTO> updatePassword(String id, String currentPsw, String newPsw){
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            if(bcryptEncoder.matches(currentPsw, user.getPassword())) {
                user.setPassword(bcryptEncoder.encode(newPsw));
                userRepo.save(user);
                return new ResponseEntity<>(modelToDTO.userToDTO(user), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<UserDTO> updateUserNIC(String id, User newUser){
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setNIC(newUser.getNIC());
            userRepo.save(user);
            return new ResponseEntity<>(modelToDTO.userToDTO(user),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<UserDTO>> getUserList() {
        List<UserDTO> userDTOList = new ArrayList<>();
        Iterable<User> userList = userRepo.findAll();
        for(User user : userList){
            userDTOList.add(modelToDTO.userToDTO(user));
        }
        return new ResponseEntity<>(userDTOList, HttpStatus.OK);
    }

    public void blacklistUser(String id){

        Optional<User> userOptional = userRepo.findById(id);
//        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setCustomerState(CustomerState.Blacklisted);
            userRepo.save(user);
//            return new ResponseEntity<>(user,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

//        try{
//            Optional<User> userOptional = userRepo.findById(id);
//            if(userOptional.isPresent()){
//                User user = userOptional.get();
//                user.setCustomerState(CustomerState.Blacklisted);
//                userRepo.save(user);
//                return new ResponseEntity<>(true,HttpStatus.OK);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return new ResponseEntity<>(false, HttpStatus.OK);
    }

}
