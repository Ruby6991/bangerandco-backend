package com.apiit.bangerandco.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserContactID implements Serializable {

    private static final long serialVersionUID=1L;

    private int mobileNo;
    private String userEmail;
}
