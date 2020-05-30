package com.apiit.bangerandco.dtos;

import com.apiit.bangerandco.enums.BookingState;
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
public class BookingDTO {
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupDateTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date dropDateTime;

    private boolean lateState;
    private boolean extendedState;

    @Temporal(TemporalType.TIMESTAMP)
    private Date extendedTime;

    @Enumerated(EnumType.STRING)
    private BookingState bookingState;

    @Temporal(TemporalType.TIMESTAMP)
    private Date bookedTime;

    private double totalAmount;
    private List<UtilityDTO> utilities;
    private VehicleDTO vehicle;
    private UserDTO user;
}
