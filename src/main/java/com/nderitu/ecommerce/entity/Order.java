package com.nderitu.ecommerce.entity;

import com.nderitu.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;


import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Table(name="orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderDescription;

    private Date date;

    private Long amount;  //amount after coupon code or discount

    private String address;

    private String paymentDetails;

   private OrderStatus orderStatus;

    private Long totalAmount; //total amount of the order

    private Long discount;

    private UUID trackingId; //generate automatic id to track the order

     //storing user details in the order

    @OneToOne (cascade = CascadeType.MERGE)//one user - one order //todo research here
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

 //  @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")  //one order can have many card items
    private List<CartItems> cartItems;


}
