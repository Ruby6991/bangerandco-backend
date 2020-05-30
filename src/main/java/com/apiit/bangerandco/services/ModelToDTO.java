package com.apiit.bangerandco.services;

import com.apiit.bangerandco.dtos.*;
import com.apiit.bangerandco.models.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ModelToDTO {

    public BookingDTO bookingToDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setId(booking.getId());
        bookingDTO.setBookedTime(booking.getBookedTime());
        bookingDTO.setBookingState(booking.getBookingState());
        bookingDTO.setDropDateTime(booking.getDropDateTime());
        bookingDTO.setExtendedState(booking.isExtendedState());
        bookingDTO.setLateState(booking.isLateState());
        bookingDTO.setTotalAmount(booking.getTotalAmount());
        bookingDTO.setExtendedTime(booking.getExtendedTime());
        bookingDTO.setPickupDateTime(booking.getPickupDateTime());
        bookingDTO.setUser(userToDTO(booking.getUser()));

        List<Utility> utilities = booking.getUtilities();
        List<UtilityDTO> utilityDTOList = new ArrayList<>();
        for(Utility utility: utilities){
            utilityDTOList.add(utilityToDTO(utility));
        }
        bookingDTO.setUtilities(utilityDTOList);

        bookingDTO.setVehicle(vehicleToDTO(booking.getVehicle()));
        return bookingDTO;
    }

    public UserDTO userToDTO(User user){
        UserDTO userDTO = new UserDTO();
        userDTO.setAddress(user.getAddress());
        userDTO.setPhoneNo(user.getPhoneNo());

//        List<Booking> bookings = user.getBookings();
//        List<BookingDTO> bookingDTOList = new ArrayList<>();
//        for(Booking booking: bookings){
//            bookingDTOList.add(bookingToDTO(booking));
//        }
//        userDTO.setBookings(bookingDTOList);

        userDTO.setCustomerState(user.getCustomerState());
        userDTO.setDateOfBirth(user.getDateOfBirth());
        userDTO.setDriversLicense(user.getDriversLicense());
        userDTO.setEmail(user.getEmail());
        userDTO.setFirstName(user.getFirstName());
        userDTO.setLastName(user.getLastName());
        userDTO.setNIC(user.getNIC());
        userDTO.setUserType(user.getUserType());
        return userDTO;
    }

    public UtilityDTO utilityToDTO(Utility utility){
        UtilityDTO utilityDTO = new UtilityDTO();
        utilityDTO.setId(utility.getId());
        utilityDTO.setQuantity(utility.getQuantity());
        utilityDTO.setUtilityAvailability(utility.isUtilityAvailability());
        utilityDTO.setUtilityName(utility.getUtilityName());
        utilityDTO.setUtilityRate(utility.getUtilityRate());
        utilityDTO.setUtilityImg(utility.getUtilityImg());
        return utilityDTO;
    }

    public VehicleDTO vehicleToDTO(Vehicle vehicle){
        VehicleDTO vehicleDTO = new VehicleDTO();
        vehicleDTO.setAvailability(vehicle.isAvailability());
        vehicleDTO.setCategory(vehicle.getCategory());
        vehicleDTO.setDescription(vehicle.getDescription());
        vehicleDTO.setFuelType(vehicle.getFuelType());
        vehicleDTO.setId(vehicle.getId());
        vehicleDTO.setImgUrl(vehicle.getImgUrl());
        vehicleDTO.setLicenseNo(vehicle.getLicenseNo());
        vehicleDTO.setMileage(vehicle.getMileage());
        vehicleDTO.setModel(vehicle.getModel());
        vehicleDTO.setQuantity(vehicle.getQuantity());
        vehicleDTO.setRates(vehicle.getRates());
        vehicleDTO.setServiceDate(vehicle.getServiceDate());
        return vehicleDTO;
    }

}
