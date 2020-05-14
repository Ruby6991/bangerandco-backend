package com.apiit.bangerandco.models;

import com.apiit.bangerandco.enums.CustomerState;
import com.apiit.bangerandco.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @Id
    @Column(name = "id")
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private int phoneNo;

    @Column
    private String NIC;

    @Column(nullable = false)
    @Size(min = 6)
    private String password;

    @Column(name = "dob")
    private Date dateOfBirth;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    @Enumerated(EnumType.STRING)
    private CustomerState customerState;

    @Column
    private String driversLicense;

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;

}
