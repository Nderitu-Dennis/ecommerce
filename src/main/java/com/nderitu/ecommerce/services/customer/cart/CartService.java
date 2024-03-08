package com.nderitu.ecommerce.services.customer.cart;

import com.nderitu.ecommerce.dto.AddProductInCartDto;
import org.springframework.http.ResponseEntity;

public interface CartService {

    ResponseEntity<?> addProductToCart(AddProductInCartDto addProductInCartDto);
}
