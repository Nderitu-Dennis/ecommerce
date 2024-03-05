package com.nderitu.ecommerce.entity;

import com.nderitu.ecommerce.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="users") //create a table called users
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //can increment the id while creating a new user
    private Long id;

    private String email;

    private String password;

    private String name;

    private UserRole role;

    @Lob     //fields that store large amount of binary/character data
   @Column(columnDefinition = "bytea")
   private byte[] image;

    public User() {
    }

   public User(byte[] image) {
       this.image = image; }

}
