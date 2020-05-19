package com.apiit.bangerandco.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UtilityDTO {
    private int id;
    private String utilityName;
    private int quantity;
    private double utilityRate;
    private boolean utilityAvailability;
    private String utilityImg;
    private List<BookingDTO> bookings;
}
