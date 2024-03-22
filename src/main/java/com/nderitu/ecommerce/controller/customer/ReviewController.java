package com.nderitu.ecommerce.controller.customer;

import com.nderitu.ecommerce.dto.OrderedProductResponseDto;
import com.nderitu.ecommerce.dto.ReviewDto;
import com.nderitu.ecommerce.services.customer.review.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customer")

public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/ordered-products/{orderId}")
    public ResponseEntity<OrderedProductResponseDto> getOrderedProductDetailsbyOrderId(@PathVariable Long orderId) {
        return ResponseEntity.ok(reviewService.getOrderedProductsDetailsByOrderId(orderId));
    }

    @PostMapping("/review")
    public ResponseEntity<?> giveReview(@ModelAttribute ReviewDto reviewDto) throws IOException {
        ReviewDto reviewDto1 = reviewService.giveReview(reviewDto);
        if(reviewDto1 ==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("something went wrong");
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewDto1);


    }
}

