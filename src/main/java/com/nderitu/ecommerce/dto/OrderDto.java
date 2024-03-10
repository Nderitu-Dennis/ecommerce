package com.nderitu.ecommerce.dto;

import com.nderitu.ecommerce.entity.CartItems;
import com.nderitu.ecommerce.entity.User;
import com.nderitu.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class OrderDto {
    private Long id;

    private String orderDescription;
    private Date date;
    private BigDecimal amount; // Changed from Long to BigDecimal
    private String address;
    private String paymentDetails;
    private OrderStatus orderStatus;
    private BigDecimal totalAmount; // Changed from Long to BigDecimal
    private BigDecimal discount; // Changed from Long to BigDecimal
    private UUID trackingId;
    private String  userName; // use this to show the orders to the admin
    private List<CartItemsDto> cartItems;
    private String couponName;
}
