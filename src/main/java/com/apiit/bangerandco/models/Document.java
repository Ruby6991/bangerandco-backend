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
@Table(name = "documents")
public class Document {

        @Id
        @GeneratedValue(strategy= GenerationType.IDENTITY)
        private Long id;

        @Column
        private String docName;

        @Column
        private String docType;

        @Column
        @Lob
        private byte[] file;

        @ManyToOne(cascade = CascadeType.DETACH)
        @JoinColumn(name = "user_id",referencedColumnName = "id")
        private User user;

}
