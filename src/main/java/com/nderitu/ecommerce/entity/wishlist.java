package com.nderitu.ecommerce.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
@Table(name = "wishlist")

public class wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    relation of wishlist entity with product

   @ManyToOne(fetch = FetchType.LAZY, optional = false)
   @JoinColumn(name="product_id", nullable = false)
   @OnDelete(action = OnDeleteAction.CASCADE)
    private Product product;

//      relation of wishlist entity with users

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


}
