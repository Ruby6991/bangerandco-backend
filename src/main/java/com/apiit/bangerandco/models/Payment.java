package com.apiit.bangerandco.models;

import com.apiit.bangerandco.enums.PaymentType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "payment_info")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    @Column(nullable = false)
    private double totalAmount;

    @Column(nullable = false)
    private boolean isPaid;

    @OneToOne
    @JoinColumn(name = "booking_id",referencedColumnName = "id")
    private Booking booking;

}
