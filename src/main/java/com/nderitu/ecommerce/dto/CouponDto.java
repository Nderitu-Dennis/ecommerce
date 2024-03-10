package com.nderitu.ecommerce.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CouponDto {

    private Long id;

    private String name;

    private String code;

    private Long discount; //it will be in %

    private Date expirationDate;
}
