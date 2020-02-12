package com.apiit.bangerandco.dtos;

import com.apiit.bangerandco.enums.PaymentType;
import com.apiit.bangerandco.models.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private int id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private double totalAmount;
    private boolean isPaid;
    private BookingDTO booking;
}
