package com.apiit.bangerandco.models;

import com.apiit.bangerandco.enums.BookingState;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
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
@Table(name = "bookings")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@bookingId")
public class Booking  implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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

    @OneToOne(mappedBy = "booking")
    private Payment payment;

    @ManyToMany
    @JoinTable(
            name = "booking_utility",
            joinColumns = {@JoinColumn(name = "BOOKING_ID",referencedColumnName = "ID")},
            inverseJoinColumns = {@JoinColumn(name = "UTILITY_ID",referencedColumnName = "ID")})
    private List<Utility> utilities;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "vehicle_id",referencedColumnName = "id")
    private Vehicle vehicle;

    @ManyToOne(cascade = CascadeType.DETACH)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;

}
