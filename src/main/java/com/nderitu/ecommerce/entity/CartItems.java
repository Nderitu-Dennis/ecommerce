package com.nderitu.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name="cart_items") // Change table name to match PostgreSQL conventions
@Data
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer price; // Change data type to Integer for better compatibility with PostgreSQL

    private Integer quantity; // Change data type to Integer for better compatibility with PostgreSQL

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="product_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY, optional=false)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;
}
