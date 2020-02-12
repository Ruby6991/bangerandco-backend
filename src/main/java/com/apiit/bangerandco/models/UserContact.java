package com.apiit.bangerandco.models;

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
@Table(name = "user_contacts")
public class UserContact {

    @EmbeddedId
    private UserContactID userContactID;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("userEmail")
    @JoinColumn(name = "userEmail",referencedColumnName = "id")
    private User user;
}
