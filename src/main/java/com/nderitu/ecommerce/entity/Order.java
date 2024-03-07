package com.nderitu.ecommerce.entity;

import com.nderitu.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;
}