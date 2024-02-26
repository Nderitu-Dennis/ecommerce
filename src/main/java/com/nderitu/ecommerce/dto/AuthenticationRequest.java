package com.nderitu.ecommerce.dto;

import lombok.Data;

@Data
public class AuthenticationRequest {

    /*dto-data transfer object->design pattern used to transfer data
   btwn diff layers of an application eg. btwn the controller & service layer*/

    private String username;
    private String password;



}
