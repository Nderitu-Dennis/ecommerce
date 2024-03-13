package com.nderitu.ecommerce.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class OrderedProductResponseDto {

    private List<ProductDto> productDtoList;

    private BigDecimal orderAmount;
}
