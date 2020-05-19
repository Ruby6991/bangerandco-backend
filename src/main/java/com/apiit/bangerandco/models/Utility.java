package com.apiit.bangerandco.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "utilities")
public class Utility implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String utilityName;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private double utilityRate;

    private String utilityImg;

    @Column(nullable = false)
    private boolean utilityAvailability;

    @ManyToMany(mappedBy = "utilities")
    private List<Booking> bookings;


}
