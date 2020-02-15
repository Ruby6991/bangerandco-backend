package com.apiit.bangerandco.dtos;

import com.apiit.bangerandco.enums.CustomerState;
import com.apiit.bangerandco.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String email;
    private String firstName;
    private String lastName;
    private String address;
    private String NIC;
    private String password;
    private Date dateOfBirth;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Enumerated(EnumType.STRING)
    private CustomerState customerState;

    private String driversLicense;
    private List<BookingDTO> bookings;

    private String jwtToken;

    public UserDTO(String jwtToken,String firstName, String lastName){
        this.jwtToken=jwtToken;
        this.firstName=firstName;
        this.lastName=lastName;
    }
}
