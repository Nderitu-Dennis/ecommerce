package com.nderitu.ecommerce.dto;

import com.nderitu.ecommerce.enums.UserRole;
import lombok.Data;

@Data
public class UserDto {

    private Long id;

    private String email;

    private String name;

    private UserRole userRole;



}
