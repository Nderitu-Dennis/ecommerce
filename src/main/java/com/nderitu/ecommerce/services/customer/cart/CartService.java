package com.nderitu.ecommerce.services.customer.cart;

import com.nderitu.ecommerce.dto.AddProductInCartDto;
import com.nderitu.ecommerce.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);

    OrderDto getCartByUserId(Long userId);

    OrderDto applyCoupon(Long userId, String code);

    OrderDto increaseProductQuantity (AddProductInCartDto addProductInCartDto);

    OrderDto decreaseProductQuantity (AddProductInCartDto addProductInCartDto);



}
