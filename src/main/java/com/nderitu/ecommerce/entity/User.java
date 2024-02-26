package com.nderitu.ecommerce.entity;

import com.nderitu.ecommerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="users") //create a table called users


public class User {
    @Setter
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //can increment the id while creating a new user
    private Long id;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private String password;

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private UserRole role;

    @Lob //fields that store large amount of binary/character data
    @Column(columnDefinition = "bytea")
   private byte[] image;

    public User() {
    }

    public User(byte[] image) {
        this.image = image;
    }

}
