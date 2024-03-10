package com.nderitu.ecommerce.entity;

import com.nderitu.ecommerce.dto.CartItemsDto;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;

@Entity
@Table(name="cart_items") // Change table name to match PostgreSQL conventions
@Data
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

   // private Integer price; // Change data type to Integer for better compatibility with PostgreSQL
    private BigDecimal price;

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

    public CartItemsDto getCartDto(){
        CartItemsDto cartItemsDto = new CartItemsDto();

        cartItemsDto.setId(id);
        cartItemsDto.setPrice(price);
        cartItemsDto.setProductId(product.getId());
        cartItemsDto.setQuantity(Long.valueOf(quantity));
        cartItemsDto.setUserId(user.getId());
        cartItemsDto.setProductName(product.getName());
        cartItemsDto.setReturnedImg(product.getImg());

        return cartItemsDto;




    }
}
