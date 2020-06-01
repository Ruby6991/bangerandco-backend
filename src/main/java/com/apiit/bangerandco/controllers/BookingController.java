package com.apiit.bangerandco.controllers;

import com.apiit.bangerandco.dtos.BookingDTO;
import com.apiit.bangerandco.models.Booking;
import com.apiit.bangerandco.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BookingController {

    @Autowired
    BookingService bookingService;

    @PutMapping("/CreateBooking")
    public ResponseEntity<Boolean> CreateBooking(@RequestBody Booking booking){
        return bookingService.createBooking(booking);
    }

    @GetMapping("/GetBooking/{id}")
    public ResponseEntity<BookingDTO> GetBooking(@PathVariable int id){
        return bookingService.getBooking(id);
    }

    @PostMapping("/GetBookingList")
    public ResponseEntity<List<BookingDTO>> getBookingList(){
        return bookingService.getBookingList();
    }

    @DeleteMapping("/DeleteBooking/{id}")
    public ResponseEntity<Boolean> DeleteBooking(@PathVariable int id){
        return bookingService.deleteBooking(id);
    }

    @PutMapping("/updateBookingState/{id}")
    public ResponseEntity<Booking> UpdateBookingState(@PathVariable int id, @RequestBody Booking booking) {
        return bookingService.updateBookingState(id, booking);
    }

    @PutMapping("/updateLateReturn/{id}")
    public ResponseEntity<Booking> UpdateLateReturn(@PathVariable int id, @RequestBody Booking booking) {
        return bookingService.updateLateReturn(id,booking);
    }

    @PutMapping("/addNewUtils/{id}")
    public ResponseEntity<Booking> AddNewUtils(@PathVariable int id, @RequestBody Booking booking) {
        return bookingService.addNewUtilities(id, booking);
    }

}
