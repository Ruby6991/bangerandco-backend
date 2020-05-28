package com.apiit.bangerandco.models;

import com.apiit.bangerandco.enums.VehicleFuelType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "vehicles")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@vehicleId")
public class Vehicle implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(nullable = false)
    private String model;

    @Column(nullable = false)
    private double rates;

    private String description;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    private VehicleFuelType fuelType;

    private int mileage;

    @Column
    private Date serviceDate;

    @Column(nullable = false)
    private boolean availability;

    @Column(nullable = false)
    private String licenseNo;

    private String imgUrl;

    @Column(nullable = false)
    private String category;

    @OneToMany(mappedBy = "vehicle")
    private List<Booking> bookings;

}
