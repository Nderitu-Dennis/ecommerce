package com.nderitu.ecommerce.services.customer.review;

import com.nderitu.ecommerce.dto.OrderedProductResponseDto;
import com.nderitu.ecommerce.dto.ReviewDto;

import java.io.IOException;

public interface ReviewService {

    OrderedProductResponseDto getOrderedProductsDetailsByOrderId(Long orderId);

    ReviewDto giveReview(ReviewDto reviewDto) throws IOException;


}
