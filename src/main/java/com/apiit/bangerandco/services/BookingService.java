package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.BookingDTO;
import com.apiit.bangerandco.enums.BookingState;
import com.apiit.bangerandco.models.Booking;
import com.apiit.bangerandco.models.Utility;
import com.apiit.bangerandco.models.Vehicle;
import com.apiit.bangerandco.repositories.BookingRepository;
import com.apiit.bangerandco.repositories.UtilityRepository;
import com.apiit.bangerandco.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    BookingRepository bookingRepo;

    @Autowired
    UtilityRepository utilityRepo;

    @Autowired
    VehicleRepository vehicleRepo;

    @Autowired
    ModelToDTO modelToDTO;

    public ResponseEntity<Boolean> createBooking(Booking newBooking){

        //add the date verification when deciding the availability
        //find a way to disable the dates of bookings that has been made

        Optional<Vehicle> vehicleOptional = vehicleRepo.findById(newBooking.getVehicle().getId());
        if(vehicleOptional.isPresent()){
            Vehicle vehicle = vehicleOptional.get();
            int newQuantity = vehicle.getQuantity()-1;
            vehicle.setQuantity(newQuantity);
            if(newQuantity==0) {
                vehicle.setAvailability(false);
            }
            vehicleRepo.save(vehicle);
        }

        List<Utility> utilities = newBooking.getUtilities();
        for(Utility utility: utilities){
            Optional<Utility> utilityOptional = utilityRepo.findById(utility.getId());
            Utility util = utilityOptional.get();
            int newQuantity = util.getQuantity()-1;
            util.setQuantity(newQuantity);
            if(newQuantity==0) {
                util.setUtilityAvailability(false);
            }
            utilityRepo.save(util);
        }

        newBooking.setBookedTime(new Date());
        newBooking.setBookingState(BookingState.Pending);
        newBooking = bookingRepo.save(newBooking);
        if(newBooking.getId()>0) {
            return new ResponseEntity<>(true, HttpStatus.OK);
        }
        return new ResponseEntity<>(false,HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<BookingDTO> getBooking(int id){
        Optional<Booking> bookingOptional = bookingRepo.findById(id);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            return new ResponseEntity<>(modelToDTO.bookingToDTO(booking), HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Boolean> deleteBooking(int id){
        try{
            bookingRepo.deleteById(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(false, HttpStatus.OK);
    }

    public ResponseEntity<Booking> cancelBooking(int id, Booking newBooking){
        Optional<Booking> bookingOptional = bookingRepo.findById(id);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setBookingState(BookingState.Cancelled);
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Booking> updateBookingState(int id, Booking newBooking){
        Optional<Booking> bookingOptional = bookingRepo.findById(id);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setBookingState(newBooking.getBookingState());
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Booking> updateLateReturn(int id, Booking newBooking){
        Optional<Booking> bookingOptional = bookingRepo.findById(id);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setLateState(newBooking.isLateState());
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Booking> addNewUtilities(int id, Booking newBooking){
        Optional<Booking> bookingOptional = bookingRepo.findById(id);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setUtilities(newBooking.getUtilities());
            booking.setTotalAmount(newBooking.getTotalAmount());
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<Booking> extendBooking(int id, Booking newBooking){
        Optional<Booking> bookingOptional = bookingRepo.findById(id);
        if(bookingOptional.isPresent()){
            Booking booking = bookingOptional.get();
            booking.setExtendedState(true);
            booking.setExtendedTime(newBooking.getExtendedTime());
            bookingRepo.save(booking);
            return new ResponseEntity<>(booking,HttpStatus.OK);
        }
        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<List<BookingDTO>> getBookingList() {
        List<BookingDTO> bookingDTOList = new ArrayList<>();
        Iterable<Booking> bookings = bookingRepo.findAll();
        for(Booking booking : bookings){
            bookingDTOList.add(modelToDTO.bookingToDTO(booking));
        }
        return new ResponseEntity<>(bookingDTOList, HttpStatus.OK);
    }

}
