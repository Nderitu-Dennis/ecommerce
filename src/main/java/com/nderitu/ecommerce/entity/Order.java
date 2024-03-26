package com.nderitu.ecommerce.entity;

import com.nderitu.ecommerce.dto.OrderDto;
import com.nderitu.ecommerce.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @OneToOne(cascade = CascadeType.MERGE) //todo research on CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coupon_id", referencedColumnName = "id")
    private Coupon coupon;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItems> cartItems = new ArrayList<>();

    public OrderDto getOrderDto(){
        OrderDto  orderDto= new OrderDto();

        orderDto.setId(id);
        orderDto.setOrderDescription(orderDescription);
        orderDto.setAddress(address);
        orderDto.setTrackingId(trackingId);
        orderDto.setAmount(amount);
        orderDto.setDate(date);
        orderDto.setOrderStatus(orderStatus);
        orderDto.setUserName(user.getName());

        if(coupon != null){
            orderDto.setCouponName(coupon.getName());
        }
        return orderDto;


    }
}