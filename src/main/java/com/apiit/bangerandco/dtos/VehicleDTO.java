package com.apiit.bangerandco.dtos;

import com.apiit.bangerandco.enums.VehicleFuelType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VehicleDTO {
    private int id;
    private String model;
    private double rates;
    private String description;
    private int quantity;

    @Enumerated(EnumType.STRING)
    private VehicleFuelType fuelType;

    private int mileage;
    private Date serviceDate;
    private boolean availability;
    private String licenseNo;
    private String imgUrl;
    private String category;
    private List<BookingDTO> bookings;
}
