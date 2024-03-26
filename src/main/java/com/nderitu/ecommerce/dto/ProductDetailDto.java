package com.nderitu.ecommerce.dto;

import lombok.Data;

import java.util.List;

@Data
public class ProductDetailDto {
//    getting product details
    private ProductDto productDto;
    private List<ReviewDto> reviewDtoList;
    private List<FAQDto> faqDtoList;

}
