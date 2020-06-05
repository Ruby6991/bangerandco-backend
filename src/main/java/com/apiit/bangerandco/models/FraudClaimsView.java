package com.apiit.bangerandco.models;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Entity
@Table(name = "fraud_claims_view")
public class FraudClaimsView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(nullable = false, name = "fraud_license")
    private String fraudLicenseView;
}

