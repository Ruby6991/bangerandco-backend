package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.BookingDTO;
import com.apiit.bangerandco.dtos.UserDTO;
import com.apiit.bangerandco.enums.BookingState;
import com.apiit.bangerandco.enums.CustomerState;
import com.apiit.bangerandco.enums.UserType;
import com.apiit.bangerandco.models.Booking;
import com.apiit.bangerandco.models.Document;
import com.apiit.bangerandco.models.User;
import com.apiit.bangerandco.models.Vehicle;
import com.apiit.bangerandco.repositories.BookingRepository;
import com.apiit.bangerandco.repositories.DocumentRepository;
import com.apiit.bangerandco.repositories.UserRepository;
import com.apiit.bangerandco.repositories.VehicleRepository;
import com.sun.istack.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.activation.DataHandler;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserRepository userRepo;

    @Autowired
    BookingRepository bookingRepo;

    @Autowired
    VehicleRepository vehicleRepo;

    @Autowired
    EmailService emailService;

    @Autowired
    DocumentRepository documentRepo;

    @Autowired
    DocumentServiceImpl documentService;

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

    public ResponseEntity<UserDTO> updateUserLicense(String id, User newUser){
        Optional<User> userOptional = userRepo.findById(id);
        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setDriversLicense(newUser.getDriversLicense());
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

    public ResponseEntity<UserDTO> updateUserState(String id, User newUser){
        Optional<User> userOptional = userRepo.findById(id);
        if(newUser.getCustomerState()== CustomerState.Blacklisted){
            List<Booking> userBookings = userOptional.get().getBookings();
            for(Booking booking: userBookings){

                if(booking.getBookingState()==BookingState.Pending){
                    booking.setBookingState(BookingState.Cancelled);
                    bookingRepo.save(booking);

                    Optional<Vehicle> vehicleOptional = vehicleRepo.findById(booking.getVehicle().getId());
                    if(vehicleOptional.isPresent()) {
                        Vehicle vehicle = vehicleOptional.get();
                        vehicle.setAvailability(true);
                        vehicleRepo.save(vehicle);
                    }
                }
            }
        }

        if(userOptional.isPresent()){
            User user = userOptional.get();
            user.setCustomerState(newUser.getCustomerState());
            userRepo.save(user);
            return new ResponseEntity<>(modelToDTO.userToDTO(user),HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public String checkUser(User user){
        String license = user.getDriversLicense();
        String userEmail = user.getEmail();
        try {
            final String url = "http://localhost:8081/CheckLicenseValidity/{licenseNo}";
            Map<String,String> params = new HashMap<>();
            params.put("licenseNo",license);

            RestTemplate restTemplate = new RestTemplate();

            String userValidity = restTemplate.getForObject(url,String.class,params);

            if(userValidity.equals("Suspended") || userValidity.equals("Stolen") || userValidity.equals("Lost")){
                alertDMW(userValidity,user);
                User blockUser = new User();
                blockUser.setCustomerState(CustomerState.Blacklisted);
                updateUserState(userEmail,blockUser);
            }
            return userValidity;
        } catch (Exception e) {
            e.printStackTrace();
            return "Exception" + e.getMessage();
        }
    }

    public boolean alertDMW(String licenseState, User user){

        String emailTo = "rabiyaf1@gmail.com";
        String subject = "Illegal Use of A "+licenseState+" License";
        String body =  "Hello Sir/Madam,\n" +
                "\n" +
                "Please find below details of an offence regarding the misuse of a "+licenseState+" license. Further, the license in question has been attached to the email for your convenience."+"\n" +
                "\n" +
                "Banger & Co DMV Registration Number: 72534KJ6G8"+ "\n"+"Date and Time of the Offence: "+new Date().toString()+"\n"+
                "\n"+
                "If you have any questions regarding the details of this email, please contact us at adminMike@banger.com.\n" +
                "\n" +
                "Best regards,\n" +
                "Mike Tyson\n" +
                "Admin\n" +
                "Banger & Co\n" +
                "45 Paternoster Square, London\n";

        Document userDoc = new Document();
        Iterable<Document> docs = documentRepo.findAll();
        if(docs!=null){
            for(Document doc : docs){
                if(doc.getUser().getEmail().equals(user.getEmail()) && doc.getDocType().equals("Drivers License")){
                    userDoc = doc;
                }
            }
        }

        byte[] byteArr = userDoc.getFile();

        String encoded = Base64.getEncoder().withoutPadding().encodeToString(byteArr);

        String base64 = encoded;
        byte[] decoder = Base64.getDecoder().decode(base64);

        Multipart multipart= new MimeMultipart();
        try {
            MimeBodyPart messagePart = new MimeBodyPart();
            messagePart.setText(body);
            MimeBodyPart attachmentPart = new MimeBodyPart();
            ByteArrayDataSource licenseImage = new ByteArrayDataSource(decoder, "image/jpeg");
            attachmentPart.setDataHandler(new DataHandler(licenseImage));
            attachmentPart.setFileName(userDoc.getDocName());
            multipart.addBodyPart(messagePart);
            multipart.addBodyPart(attachmentPart);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        boolean successful=emailService.sendMailWithAttachment(emailTo,subject,body,multipart);

        if(successful){
            return true;
        }
        return false;
    }

}
