package com.nderitu.ecommerce.services.customer.review;

import com.nderitu.ecommerce.dto.OrderedProductResponseDto;

public interface ReviewService {

    OrderedProductResponseDto getOrderedProductsDetailsByOrderId(Long orderId);
}
